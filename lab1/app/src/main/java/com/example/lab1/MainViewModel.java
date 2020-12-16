package com.example.lab1;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

public class MainViewModel extends AndroidViewModel
{
    private final MutableLiveData<String> spinnerInputLiveData = new MutableLiveData<>("");
    private final MutableLiveData<String> spinnerOutputLiveData = new MutableLiveData<>("");
    private final MutableLiveData<String> inputEditLiveData = new MutableLiveData<>("");
    private final MutableLiveData<String> outputEditLiveData = new MutableLiveData<>("");

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSpinnerInputLiveData(String item)
    {
        spinnerInputLiveData.setValue(item);
    }

    public void setSpinnerOutputLiveData(String item)
    {
        spinnerOutputLiveData.setValue(item);
    }

    public void ReplaceValues()
    {
        String temp = inputEditLiveData.getValue();
        inputEditLiveData.setValue(outputEditLiveData.getValue());
        outputEditLiveData.setValue(temp);
        temp = spinnerInputLiveData.getValue();
        spinnerInputLiveData.setValue(spinnerOutputLiveData.getValue());
        spinnerOutputLiveData.setValue(temp);
    }

    public void addValue(String str)
    {
        inputEditLiveData.setValue(inputEditLiveData.getValue() + str);
    }


    public void addPoint()
    {
        if(Objects.requireNonNull(inputEditLiveData.getValue()).length() > 0 && inputEditLiveData.getValue().indexOf(".") < 1)
        {
            inputEditLiveData.setValue(inputEditLiveData.getValue() + ".");
        }
    }

    public void ClearData()
    {
        inputEditLiveData.setValue("");
    }

    public MutableLiveData<String> getInputEditLiveData() {
        return inputEditLiveData;
    }

    public MutableLiveData<String> getOutputEditLiveData() {
        return outputEditLiveData;
    }


    public void convert()
    {
        double firstkoeff = 1.0;
        double secondkoeff = 1.0;

        switch (Objects.requireNonNull(spinnerInputLiveData.getValue()))
        {
            case "Meters":
            case "Grams":
            case "Rubles":
                firstkoeff = 1.0;
                break;
            case "Kilometers":
            case "Kilograms":
                firstkoeff = 1000.0;
                break;
            case "Miles":
                firstkoeff = 1609.64;
                break;
            case "Pounds":
                firstkoeff = 453.592;
                break;
            case "Dollars" :
                firstkoeff = 2.62;
                break;
            case "Euro":
                firstkoeff = 3.09;
                break;
            default:
                firstkoeff = 1.00;
                break;
        }

        switch (Objects.requireNonNull(spinnerOutputLiveData.getValue()))
        {
            case "Meters":
            case "Grams":
            case "Rubles":
                secondkoeff = 1.0;
                break;
            case "Kilometers":
            case "Kilograms":
                secondkoeff = 1000.0;
                break;
            case "Miles":
                secondkoeff = 1609.64;
                break;
            case "Pounds":
                secondkoeff = 453.592;
                break;
            case "Dollars" :
                secondkoeff = 2.62;
                break;
            case "Euro":
                secondkoeff = 3.09;
                break;
            default:
                secondkoeff = 1.00;
                break;
        }

        if (Objects.requireNonNull(inputEditLiveData.getValue()).length() > 0)
        {
            outputEditLiveData.setValue(String.valueOf(String.format("%.2f", (firstkoeff / secondkoeff) * Double.parseDouble(Objects.requireNonNull(inputEditLiveData.getValue())))));
        }

        else {
            Toast toast = Toast.makeText(getApplication(), "Empty set", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
