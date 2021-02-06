package com.example.lab2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.DB.DBHelper;
import com.example.lab2.App;
import com.example.lab2.DB.DBModel;
import com.example.lab2.Timer.TimerAdapter;
import com.example.lab2.R;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();


        list = findViewById(R.id.ListTimer);
        TimerAdapter adapter = new TimerAdapter(this, R.layout.timers
                , db.dbTimer().getAll(), db);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                DBModel training = (DBModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), TrainingActivity.class);
                intent.putExtra("timerId", training.Id);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonAddTimer).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), TimerCreateActivity.class);
            intent.putExtra("timerId", new int[]{0,0});
            startActivity(intent);
        });

        findViewById(R.id.SettingsButton).setOnClickListener(i -> {
            Intent Settings = new Intent(this, com.example.lab2.Activity.SettingsActivity.class);
            startActivityForResult(Settings, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}