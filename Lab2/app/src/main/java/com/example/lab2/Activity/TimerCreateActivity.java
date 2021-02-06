package com.example.lab2.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.lab2.App;
import com.example.lab2.DB.DBHelper;
import com.example.lab2.DB.DBModel;
import com.example.lab2.R;
import com.example.lab2.ViewModels.TimerViewModel;

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;


public class TimerCreateActivity extends AppCompatActivity {

    private DBHelper db;
    private TimerViewModel viewModel;

    Button btnPrepPlus;
    Button btnPrepMinus;
    Button btnTrainingPlus;
    Button btnTrainingMinus;
    Button btnRelaxPlus;
    Button btnRelaxMinus;
    Button btnCyclePlus;
    Button btnCycleMinus;
    EditText inputName;
    EditText inputPrep;
    EditText inputTraining;
    EditText inputRelax;
    EditText inputCycle;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;


    HSLColorPickerSeekBar bar;

    DBModel dbModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_create);

        viewModel = ViewModelProviders.of(this).get(TimerViewModel.class);
        db = App.getInstance().getDatabase();
        FindControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int[] id = (int[])bundle.get("timerId");

        if(id[1] == 1){
            dbModel = db.dbTimer().getById(id[0]);
            initInputs(dbModel);
        }

        viewModel.getName().observe(this, val -> inputName.setText(val));
        viewModel.getPreparation().observe(this, val -> inputPrep.setText(val.toString()));
        viewModel.getTrainingTime().observe(this, val -> inputTraining.setText(val.toString()));
        viewModel.getRelaxTime().observe(this, val -> inputRelax.setText(val.toString()));
        viewModel.getCycles().observe(this, val -> inputCycle.setText(val.toString()));
        btnPrepPlus.setOnClickListener(i -> viewModel.setIncrementPreparation());
        btnPrepMinus.setOnClickListener(i -> viewModel.setDecrementPreparation());

        btnTrainingPlus.setOnClickListener(i -> viewModel.setIncrementTrainingTime());
        btnTrainingMinus.setOnClickListener(i -> viewModel.setDecrementTrainingTime());

        btnRelaxPlus.setOnClickListener(i -> viewModel.setIncrementRelaxTime());
        btnRelaxMinus.setOnClickListener(i -> viewModel.setDecrementRelaxTime());

        btnCyclePlus.setOnClickListener(i -> viewModel.setIncrementCycle());
        btnCycleMinus.setOnClickListener(i -> viewModel.setDecrementCycle());


        inputName.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                viewModel.setName(inputName.getText().toString());
                if(keyCode == 4)
                {
                    Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(backIntent);
                    finish();
                    return true;
                }
                return true;
            }
            return false;
        });

        findViewById(R.id.btnCancel).setOnClickListener(i -> {
            quitAndCancel();
        });


        findViewById(R.id.submit).setOnClickListener(i -> {
            quitAndSave();
        });
    }

    private void quitAndCancel() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                TimerCreateActivity.this);
        quitDialog.setTitle(getResources().getString(R.string.Cancel));
        quitDialog.setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
        quitDialog.setNegativeButton(getResources().getString(R.string.No), (dialog, which) -> {
        });
        quitDialog.show();
    }

    private void quitAndSave() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                TimerCreateActivity.this);
        quitDialog.setTitle(getResources().getString(R.string.SaveMess));
        quitDialog.setPositiveButton(getResources().getString(R.string.Yes), (dialog, which) -> {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            int[] id = (int[])bundle.get("timerId");

            if(id[1] == 1){
                dbModel = db.dbTimer().getById(id[0]);
                initInputs(dbModel);
            }
            if (id[1] != 1) {
                DBModel dbModel = new DBModel();
                dbModel.Name = inputName.getText().toString();
                dbModel.Preparation = Integer.parseInt(inputPrep.getText().toString());
                dbModel.Training = Integer.parseInt(inputTraining.getText().toString());
                dbModel.Relax = Integer.parseInt(inputRelax.getText().toString());
                dbModel.Cycles = Integer.parseInt(inputCycle.getText().toString());
                IntegerHSLColor ii = bar.getPickedColor();

                dbModel.Color = Color.HSVToColor(new float[]{ii.getFloatH(), ii.getFloatL(), ii.getFloatS()});
                db.dbTimer().insert(dbModel);
            }
            else {
                dbModel.Name = inputName.getText().toString();
                dbModel.Preparation = Integer.parseInt(inputPrep.getText().toString());
                dbModel.Training = Integer.parseInt(inputTraining.getText().toString());
                dbModel.Relax = Integer.parseInt(inputRelax.getText().toString());
                dbModel.Cycles = Integer.parseInt(inputCycle.getText().toString());
                IntegerHSLColor ii = bar.getPickedColor();
                dbModel.Color = Color.HSVToColor(new float[]{ii.getFloatH(), ii.getFloatL(), ii.getFloatS()});
                db.dbTimer().update(dbModel);
            }
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
        quitDialog.setNegativeButton(getResources().getString(R.string.No), (dialog, which) -> {
            onBackPressed();
        });
        quitDialog.show();
    }

    private void initInputs(DBModel dbModel){
        viewModel.setName(dbModel.Name);
        viewModel.setPrep(dbModel.Preparation);
        viewModel.setTraining(dbModel.Training);
        viewModel.setRelax(dbModel.Relax);
        viewModel.setCycle(dbModel.Cycles);
        viewModel.setColor(dbModel.Color);
        bar.setPickedColor(convertToIntegerHSLColor(dbModel.Color));
    }

    private void FindControls(){
        btnPrepPlus = findViewById(R.id.btnPrepPlus);
        btnPrepMinus = findViewById(R.id.btnPrepMinus);
        btnTrainingPlus = findViewById(R.id.btnWorkPlus);
        btnTrainingMinus = findViewById(R.id.btnWorkMinus);
        btnRelaxPlus = findViewById(R.id.btnRestPlus);
        btnRelaxMinus = findViewById(R.id.btnRestMinus);
        btnCyclePlus = findViewById(R.id.btnCyclePlus);
        btnCycleMinus = findViewById(R.id.btnCycleMinus);
        inputName = findViewById(R.id.inputName);
        inputPrep = findViewById(R.id.inputPrep);
        inputTraining = findViewById(R.id.inputWork);
        inputRelax = findViewById(R.id.inputRest);
        inputCycle = findViewById(R.id.inputCycle);
        imageView1 = findViewById(R.id.icon);
        imageView2 = findViewById(R.id.icon2);
        imageView3 = findViewById(R.id.icon3);
        imageView4 = findViewById(R.id.icon4);
        bar = findViewById(R.id.color_seek_bar);
    }

    private IntegerHSLColor convertToIntegerHSLColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        IntegerHSLColor integerHSLColor = new IntegerHSLColor();
        integerHSLColor.setFloatH(hsv[0]);
        integerHSLColor.setFloatL(hsv[1]);
        integerHSLColor.setFloatS(hsv[2]);
        return integerHSLColor;
    }

}