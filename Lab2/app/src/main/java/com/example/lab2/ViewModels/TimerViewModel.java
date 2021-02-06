package com.example.lab2.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimerViewModel extends ViewModel {
    private MutableLiveData<String> Name = new MutableLiveData<>(null);
    private MutableLiveData<Integer> Preparation = new MutableLiveData<>(10);
    private MutableLiveData<Integer> Training = new MutableLiveData<>(20);
    private MutableLiveData<Integer> Relax = new MutableLiveData<>(10);
    private MutableLiveData<Integer> Cycles = new MutableLiveData<>(2);
    private final MutableLiveData<Integer> Color = new MutableLiveData<>(-16777216);


    public void setName(String name) {
        Name.setValue(name);
    }

    public MutableLiveData<String> getName() {
        return Name;
    }


    public void setColor(int color) {
        Color.setValue(color);
    }

    public MutableLiveData<Integer> getColor() {
        return Color;
    }




    public MutableLiveData<Integer> getPreparation() {
        return Preparation;
    }

    public void setPreparation(MutableLiveData<Integer> preparation) {
        Preparation = preparation;
    }

    public void setIncrementPreparation() {
        Preparation.setValue(Preparation.getValue() + 1);
    }

    public void setDecrementPreparation() {
        if(Preparation.getValue() != 0) {
            Preparation.setValue(Preparation.getValue() - 1);
        }
    }

    public void setPrep(int prep) {
        Preparation.setValue(prep);
    }




    public void setTrainingTime(MutableLiveData<Integer> TrainingTime) {
        Training = TrainingTime;
    }

    public MutableLiveData<Integer> getTrainingTime() {
        return Training;
    }

    public void setIncrementTrainingTime( ) {
        Training.setValue(Training.getValue() + 1);
    }

    public void setDecrementTrainingTime() {
        if(Training.getValue() != 0) {
            Training.setValue(Training.getValue() - 1);
        }
    }

    public void setTraining(int work) {
        Training.setValue(work);
    }




    public void setRelaxTime(MutableLiveData<Integer> relaxTime) {
        Relax = relaxTime;
    }

    public void setRelax(int relax) {
        Relax.setValue(relax);
    }

    public MutableLiveData<Integer> getRelaxTime() {
        return Relax;
    }

    public void setIncrementRelaxTime() {
        Relax.setValue(Relax.getValue() + 1);
    }

    public void setDecrementRelaxTime() {
        if(Relax.getValue() != 0) {
            Relax.setValue(Relax.getValue() - 1);
        }
    }




    public void setCycles(MutableLiveData<Integer> cycles) {
        Cycles = cycles;
    }

    public void setCycle(int cycle) {
        Cycles.setValue(cycle);
    }

    public MutableLiveData<Integer> getCycles() {
        return Cycles;
    }

    public void setIncrementCycle() {
        Cycles.setValue(Cycles.getValue() + 1);
    }

    public void setDecrementCycle() {
        if(Cycles.getValue() != 0) {
            Cycles.setValue(Cycles.getValue() - 1);
        }
    }

}