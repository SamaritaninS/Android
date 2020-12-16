package com.example.timer.Model;

import java.io.Serializable;

public class Timer implements Serializable {
    private int timerId;
    private  String timerName;
    private  String preparationTime;
    private  String warmTime;
    private  String workTime;
    private  String relaxationTime;
    private  String cycleCount;
    private  String setCount;
    private  String pauseTime;
    private  String color;

    public Timer(){}

    public Timer(String timerName, String preparationTime,
                 String warmTime, String workTime,
                 String relaxationTime, String cycleCount,
                 String setCount, String pauseTime, String color)
    {
        this.timerName = timerName;
        this.preparationTime = preparationTime;
        this.warmTime = warmTime;
        this.workTime = workTime;
        this.relaxationTime = relaxationTime;
        this.cycleCount = cycleCount;
        this.setCount = setCount;
        this.pauseTime = pauseTime;
        this.color = color;
    }

    public Timer( int timerId, String timerName,
                  String preparationTime, String warmTime,
                  String workTime, String relaxationTime,
                  String cycleCount, String setCount,
                  String pauseTime, String color)
    {
        this.timerName = timerName;
        this.timerId = timerId;
        this.preparationTime = preparationTime;
        this.warmTime = warmTime;
        this.workTime = workTime;
        this.relaxationTime = relaxationTime;
        this.cycleCount = cycleCount;
        this.setCount = setCount;
        this.pauseTime = pauseTime;
        this.color = color;
    }

    public int getTimerId() {
        return timerId;
    }

    public void setTimerId(int timerId) {
        this.timerId = timerId;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getWarmTime() {
        return warmTime;
    }

    public void setWarmTime(String warmTime) {
        this.warmTime = warmTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getRelaxationTime() {
        return relaxationTime;
    }

    public void setRelaxationTime(String relaxationTime) {
        this.relaxationTime = relaxationTime;
    }

    public String getCycleCount() {
        return cycleCount;
    }

    public void setCycleCount(String cycleCount) {
        this.cycleCount = cycleCount;
    }

    public String getSetCount() {
        return setCount;
    }

    public void setSetCount(String setCount) {
        this.setCount = setCount;
    }

    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }

    public String getTimerName() {
        return timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    @Override
    public String toString()  {
        return this.timerName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
