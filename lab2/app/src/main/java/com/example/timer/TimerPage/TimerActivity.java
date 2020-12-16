package com.example.timer.TimerPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.timer.ViewModel.MainViewModel;
import com.example.timer.R;
import com.example.timer.Service.TimerService;
import com.example.timer.Model.Timer;

import java.util.List;

public class TimerActivity extends AppCompatActivity {

    private Timer timer;
    public ListView listView;
    public TextView textView_timer;
    public List<String> timerList;
    public ArrayAdapter<String> listViewAdapter;
    public MainViewModel mainViewModel ;
    public Button btnStart, btnStop;
    public ConstraintLayout constraintLayout;
    Intent intentService;

    BroadcastReceiver br;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    public final static String PARAM_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainViewModel  = ViewModelProviders.of(this).get(MainViewModel.class);
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
        setContentView(R.layout.activity_timer);

        Intent intent = this.getIntent();
        this.timer = (Timer) intent.getSerializableExtra("timer");
        mainViewModel.initializationTimerIntegerList(timer);
        timerList = mainViewModel.getTimerStringList(timer);

        listView = findViewById(R.id.listView);
        constraintLayout = findViewById(R.id.Constraint);
        btnStart = findViewById(R.id.button_start);
        textView_timer = findViewById(R.id.textView_timer);
        btnStop = findViewById(R.id.button_stop);

        listViewAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, timerList);
        listView.setAdapter(listViewAdapter);
        listView.setBackgroundColor(Color.parseColor(timer.getColor()));
        constraintLayout.setBackgroundColor(Color.parseColor(timer.getColor()));

        intentService = new Intent(this, TimerService.class).putExtra("list", mainViewModel.getIntList());
        btnStart.setOnClickListener(item -> {
            intentService.putExtra("operationCode", 1);
            startService(intentService);
        });

        btnStop.setOnClickListener(item -> {
            intentService.putExtra("operationCode", 2);
            startService(intentService);
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                intentService.putExtra("operationCode", 3).putExtra("position", position);
                startService(intentService);
            }
        });
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int result = intent.getIntExtra(PARAM_RESULT, 0);
                textView_timer.setText("" + result);
            }
        };
        IntentFilter intFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        stopService(intentService);
    }

    @Override
    public void finish() {
        super.finish();
        intentService.putExtra("operationCode", 2);
        startService(intentService);
    }
}