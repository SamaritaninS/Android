package com.example.lab2.Timer;

import android.app.Service;
import android.content.Intent;
import android.media.SoundPool;
import android.os.IBinder;

import com.example.lab2.Activity.TrainingActivity;
import com.example.lab2.R;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class Timer extends Service {

    ScheduledExecutorService service;
    SoundPool soundPool;
    int soundStart;
    int soundStop;
    int current_time;

    String name;
    ScheduledFuture<?> scheduledFuture;

    public void onCreate() {
        super.onCreate();
        soundPool = new SoundPool.Builder()
                        .setMaxStreams(5)
                        .build();
        soundStart = soundPool.load(this, R.raw.start_sound, 1);
        soundStop = soundPool.load(this, R.raw.stop_sound, 1);
        service = Executors.newScheduledThreadPool(1);
    }

    public void onDestroy() {
        service.shutdownNow();
        scheduledFuture.cancel(true);
        Intent intent = new Intent(TrainingActivity.BROADCAST_ACTION);
        intent.putExtra(TrainingActivity.CURRENT_ACTION, "pause");
        intent.putExtra(TrainingActivity.NAME_ACTION, name);
        intent.putExtra(TrainingActivity.TIME_ACTION, Integer.toString(current_time));
        sendBroadcast(intent);
        super.onDestroy();

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int time = Integer.parseInt(intent.getStringExtra(TrainingActivity.START_TIME));
        name = intent.getStringExtra(TrainingActivity.NAME_ACTION);
        MyTimerTask mr = new MyTimerTask(startId, time, name);
        if (scheduledFuture != null) {
            service.schedule(() -> {
                scheduledFuture.cancel(true);
                scheduledFuture = service.scheduleAtFixedRate(mr, 0, time + 1, TimeUnit.SECONDS);
            }, 1500, TimeUnit.MILLISECONDS);
        } else {
            scheduledFuture = service.scheduleAtFixedRate(mr, 0, time + 1, TimeUnit.SECONDS);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    class MyTimerTask extends TimerTask {

        int time;
        int startId;
        String name;

        public MyTimerTask(int startId, int time, String name) {
            this.time = time;
            this.startId = startId;
            this.name = name;
        }


        public void run() {
            Intent intent = new Intent(TrainingActivity.BROADCAST_ACTION);
            if (name.equals(getResources().getString(R.string.Finish))) {
                intent.putExtra(TrainingActivity.CURRENT_ACTION, "training");
                intent.putExtra(TrainingActivity.NAME_ACTION, name);
                intent.putExtra(TrainingActivity.TIME_ACTION, "");
                sendBroadcast(intent);
            }
            try {
                for (current_time = time; current_time > 0; current_time--) {
                    intent.putExtra(TrainingActivity.CURRENT_ACTION, "training");
                    intent.putExtra(TrainingActivity.NAME_ACTION, name);
                    intent.putExtra(TrainingActivity.TIME_ACTION, Integer.toString(current_time));
                    sendBroadcast(intent);
                    TimeUnit.SECONDS.sleep(1);
                    signal_sound(current_time);
                }
                intent = new Intent(TrainingActivity.BROADCAST_ACTION);
                intent.putExtra(TrainingActivity.CURRENT_ACTION, "clear");
                sendBroadcast(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void signal_sound(int time) {
            if (time <= 4) {
                if (time == 1)
                    soundPool.play(soundStop, 1, 1, 0, 0, 1);
                else
                    soundPool.play(soundStart, 1, 1, 0, 0, 1);
            }
        }

    }
}