package com.example.timer.SettingsPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.timer.DBHelper.DatabaseHelper;
import com.example.timer.MainPage.MainActivity;
import com.example.timer.R;
import com.example.timer.ViewModel.MainViewModel;

import java.util.Locale;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Locale locale = new Locale(prefs.getString("language", "eu-US"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        float coef = (float)prefs.getInt("size", 1);
        configuration.locale = locale;
        configuration.fontScale = coef / 10;
        getBaseContext().getResources().updateConfiguration(configuration, null);
        if(prefs.getString("theme", "D").equals("D"))
        {
            setTheme(R.style.AppThemeDark);
        }
        else if(prefs.getString("theme", "D").equals("L"))
        {
            setTheme(R.style.AppThemeLite);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_clear);
        addPreferencesFromResource(R.xml.settings);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if(prefs.getString("theme", "D").equals("D"))
        {
            setTheme(R.style.AppThemeDark);
        }
        else if(prefs.getString("theme", "D").equals("L"))
        {
            setTheme(R.style.AppThemeLite);
        }
        Locale locale = new Locale(prefs.getString("language", "eu-US"));
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        float coef = (float)prefs.getInt("size", 1);
        configuration.fontScale = coef / 10;
        getBaseContext().getResources().updateConfiguration(configuration, null);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button_clear)
        {
            DatabaseHelper db = new DatabaseHelper(this);
            db.deleteTimers();
            Log.d("delete", "Yes");
        }
    }

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}