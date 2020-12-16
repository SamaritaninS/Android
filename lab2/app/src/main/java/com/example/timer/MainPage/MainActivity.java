package com.example.timer.MainPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.EditPage.CreateActivity;
import com.example.timer.R;
import com.example.timer.Model.Timer;
import com.example.timer.Service.TimerService;
import com.example.timer.SettingsPage.SettingsActivity;
import com.example.timer.TimerPage.TimerActivity;
import com.example.timer.ViewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_DELETE = 444;
    private static final int MY_REQUEST_CODE = 1000;

    public final List<Timer> timerList = new ArrayList<Timer>();
    public ArrayAdapter<Timer> listViewAdapter;
    public ListView listView;
    public MainViewModel mainViewModel;
    Button btnAdd, btnClear;

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
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        btnAdd   = findViewById(R.id.button_add);
        timerList.addAll(mainViewModel.getTimerList());
        listViewAdapter = new ArrayAdapter<Timer>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, timerList);
        listView.setAdapter(listViewAdapter);
        registerForContextMenu(listView);
        btnAdd.setOnClickListener(item -> {
            Intent intent = new Intent(this, CreateActivity.class);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings :
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle(R.string.select);

        menu.add(0, MENU_ITEM_VIEW , 0, R.string.view_timer);
        menu.add(0, MENU_ITEM_EDIT , 2, R.string.edit_timer);
        menu.add(0, MENU_ITEM_DELETE, 4, R.string.delete_timer);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Timer selectedTimer = (Timer) listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Intent intent = new Intent(this, TimerActivity.class);
            intent.putExtra("timer", selectedTimer);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, CreateActivity.class);
            intent.putExtra("timer", selectedTimer);
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            new AlertDialog.Builder(this)
                    .setMessage(selectedTimer.getTimerName() + ". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mainViewModel.deleteTimer(selectedTimer);
                            timerList.remove(selectedTimer);
                            listViewAdapter.notifyDataSetChanged();
                            btnClear.notify();
                            btnAdd.notify();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}