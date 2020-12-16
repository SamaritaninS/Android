package com.example.timer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.timer.TimerPage.TimerActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimerService extends Service {

    public static final String ServiceIntent = "custom.MY_SERVICE";
    int[] timerList;
    private int position;
    private int timerBuffer;
    private boolean isTimerStop = false;
    private boolean isStarted = false;
    int streamIDShot;
    int operationCode;
    CountDownTimer countDownTimer;
    SoundPool sp =  new SoundPool(1, AudioManager.STREAM_MUSIC, 0);;


    public void onCreate() {
        super.onCreate();
        try {
            streamIDShot = sp.load(getAssets().openFd("sound.mp3"), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        timerList = intent.getIntArrayExtra("list");
        operationCode = intent.getIntExtra("operationCode", 0);
        if(countDownTimer != null)
        {
            countDownTimer.cancel();
        }

        if(operationCode == 3)
        {
            position = intent.getIntExtra("position", 0);
            operationCode = 1;
        }
        run();
        isStarted = true;
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void run() {
        if(operationCode == 1)
        {
            int second;
            if(isTimerStop)
            {
                second = timerBuffer;
                isTimerStop = false;
            }
            else
            {
                second = timerList[position];
            }
            countDownTimer = new CountDownTimer(second, 100) {
                @Override
                public void onTick(long l) {
                    timerUpdate((int) l);
                    timerBuffer = (int)l;
                }

                @Override
                public void onFinish() {
                    if(timerList.length - 1> position)
                    {
                        position++;
                        countDownTimer.cancel();
                        sp.play(streamIDShot, 1, 1, 0, 0, 1);
                        run();
                    }
                    else
                    {
                        position = 0;
                        sp.play(streamIDShot, 1, 1, 0, 0, 1);
                        timerUpdate(0);
                        stopSelf();
                    }
                }
            }.start();
        }
        else if(operationCode == 2)
        {
            if(countDownTimer != null)
            {
                countDownTimer.cancel();
                isTimerStop = true;
            }
            else
            {
                Toast toast = Toast.makeText(getApplication(), "Timer not started", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void timerUpdate(int time){

        Intent intent = new Intent(TimerActivity.BROADCAST_ACTION);
        intent.putExtra(TimerActivity.PARAM_RESULT, time / 1000);
        sendBroadcast(intent);
    }
}
