package com.example.timer.EditPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.Pallet.Pallet;
import com.example.timer.Pallet.PalletAdapter;
import com.example.timer.R;
import com.example.timer.Model.Timer;
import com.example.timer.ViewModel.MainViewModel;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private EditText editName, editPreparation, editWarm, editWork, editRelaxationTime, editCycleCount, editSetCount, editPause;
    private Button buttonApply;
    private Timer timer;
    private int mode;
    public MainViewModel mainViewModel;
    String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefs.getString("theme", "D").equals("D"))
        {
            setTheme(R.style.AppThemeDark);
        }
        else if(prefs.getString("theme", "D").equals("L"))
        {
            setTheme(R.style.AppThemeLite);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mainViewModel  = ViewModelProviders.of(this).get(MainViewModel.class);


        GridView palletGridView = (GridView) findViewById(R.id.palletGridView);
        ArrayList<Pallet> pallets = Pallet.getPallet();
        PalletAdapter palletAdapter = new PalletAdapter(this, pallets);
        palletGridView.setAdapter(palletAdapter);

        this.editName = (EditText)this.findViewById(R.id.edit_name);
        this.editPreparation = (EditText)this.findViewById(R.id.edit_preparation);
        this.editWarm = (EditText)this.findViewById(R.id.edit_warm);
        this.editWork = (EditText)this.findViewById(R.id.edit_work);
        this.editRelaxationTime = (EditText)this.findViewById(R.id.edit_relaxation);
        this.editCycleCount = (EditText)this.findViewById(R.id.edit_cycle);
        this.editSetCount = (EditText)this.findViewById(R.id.edit_set);
        this.editPause = (EditText)this.findViewById(R.id.edit_pause);

        this.buttonApply = (Button)findViewById(R.id.button_apply);
        this.buttonApply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                buttonSaveClicked();
                buttonCancelClicked();
            }
        });
        palletGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                color = pallets.get(position).getColor();
            }
        });

        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        if(timer == null)  {
            this.mode = MODE_CREATE;
        } else{
            this.mode = MODE_EDIT;
            this.editName.setText(timer.getTimerName());
            this.editPreparation.setText(timer.getPreparationTime());
            this.editWarm.setText(timer.getWarmTime());
            this.editWork.setText(timer.getWorkTime());
            this.editRelaxationTime.setText(timer.getRelaxationTime());
            this.editCycleCount.setText(timer.getCycleCount());
            this.editSetCount.setText(timer.getSetCount());
            this.editPause.setText(timer.getPauseTime());
            color = timer.getColor();
        }
    }

    public void buttonSaveClicked()  {
        String name = this.editName.getText().toString();
        String preparation = this.editPreparation.getText().toString();
        String warm = this.editWarm.getText().toString();
        String work = this.editWork.getText().toString();
        String relaxation = this.editRelaxationTime.getText().toString();
        String cycle = this.editCycleCount.getText().toString();
        String set = this.editSetCount.getText().toString();
        String pause = this.editPause.getText().toString();

        if(name.equals("") || preparation.equals("") || warm.equals("")
                || work.equals("") || relaxation.equals("") || cycle.equals("")
                || set.equals("") || pause.equals("") || color == null){
            Toast.makeText(getApplicationContext(),
                    "Please enter empty edit", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mode == MODE_CREATE )
        {
            mainViewModel.addTimer(name, preparation, warm, work, relaxation, cycle, set, pause, color);
        }
        else
        {
           mainViewModel.updateTimer(timer, name, preparation, warm, work, relaxation, cycle, set, pause, color);
        }
        this.onBackPressed();
    }


    public void buttonCancelClicked()  {
        this.onBackPressed();
    }

}