package com.example.timer.ViewModel;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.Model.Timer;
import com.example.timer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {

    List<Integer> timerList = new ArrayList<Integer>();
    DatabaseHelper db = new DatabaseHelper(getApplication());

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public Configuration getConfiguration()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        float coef = (float)prefs.getInt("size", 1);
        Locale locale = new Locale(prefs.getString("language", "eu-US"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        configuration.fontScale = coef / 10;
        return configuration;
    }

    public void addTimer(String timerName, String preparationTime,
                         String warmTime, String workTime,
                         String relaxationTime, String cycleCount,
                         String setCount, String pauseTime, String color)
    {
        Timer timer= new Timer(timerName, preparationTime, warmTime, workTime, relaxationTime, cycleCount, setCount, pauseTime, color);
        db.addTimer(timer);
    }

    public void updateTimer(Timer timer, String timerName, String preparationTime,
                            String warmTime, String workTime,
                            String relaxationTime, String cycleCount,
                            String setCount, String pauseTime, String color)
    {
        timer.setTimerName(timerName);
        timer.setPreparationTime(preparationTime);
        timer.setWarmTime(warmTime);
        timer.setWorkTime(workTime);
        timer.setRelaxationTime(relaxationTime);
        timer.setCycleCount(cycleCount);
        timer.setSetCount(setCount);
        timer.setPauseTime(pauseTime);
        timer.setColor(color);
        db.updateTimer(timer);
    }

    public List<Timer> getTimerList()
    {
        return db.getAllTimer();
    }

    public void deleteTimers()
    {
        db.deleteTimers();
    }

    public void deleteTimer(Timer timer)  {
        db.deleteTimer(timer);
        timerList.remove(timer);
    }

    public int[] getIntList()
    {
        int count = timerList.size();
        int[] timers = new int[count];
        for(int i = 0; i < count; i++)
        {
            timers[i] = timerList.get(i);
        }
        return timers;
    }

    public void initializationTimerIntegerList(Timer timer)
    {
        int countCycle = Integer.parseInt(timer.getCycleCount());
        int countSet = Integer.parseInt(timer.getSetCount());
        int count = 1;
        for(int i = 0; i < countSet; i++)
        {
            timerList.add(Integer.parseInt(timer.getPreparationTime()) * 1000);
            count++;
            for(int j = 0; j < countCycle; j++)
            {
                timerList.add(Integer.parseInt(timer.getWarmTime()) * 1000);
                count++;
                timerList.add(Integer.parseInt(timer.getWorkTime()) * 1000);
                count++;
                timerList.add(Integer.parseInt(timer.getRelaxationTime()) * 1000);
                count++;
            }
            timerList.add(Integer.parseInt(timer.getPauseTime()) * 1000);
            count++;
        }
    }

    public List<String> getTimerStringList(Timer timer)
    {
        List<String> stringTimerList = new ArrayList<String>();
        List<String> phase = new ArrayList<String>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplication());
        switch (prefs.getString("language", "eu-US"))
        {
            case "ru":
                phase.add(". Подготовка : ");
                phase.add(". Разминка : ");
                phase.add(". Работа : ");
                phase.add(". Отдых : ");
                phase.add(". Пауза : ");
                break;
            case "en-US":
                phase.add(". Preparation : ");
                phase.add(". Warm : ");
                phase.add(". Work : ");
                phase.add(". Relaxation : ");
                phase.add(". PauseTime : ");
                break;
        }
        int countCycle = Integer.parseInt(timer.getCycleCount());
        int countSet = Integer.parseInt(timer.getSetCount());
        int count = 1;
        for(int i = 0; i < countSet; i++)
        {
            stringTimerList.add(count + phase.get(0) + timer.getPreparationTime());
            count++;
            for(int j = 0; j < countCycle; j++)
            {
                stringTimerList.add(count + phase.get(1) + timer.getWarmTime());
                count++;
                stringTimerList.add(count + phase.get(2) + timer.getWorkTime());
                count++;
                stringTimerList.add(count + phase.get(3)  + timer.getRelaxationTime());
                count++;
            }
            stringTimerList.add(count + phase.get(4) + timer.getPauseTime());
            count++;
        }
        return stringTimerList;
    }
}
