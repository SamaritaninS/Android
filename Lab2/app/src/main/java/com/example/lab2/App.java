package com.example.lab2;

import android.app.Application;
import androidx.room.Room;
import com.example.lab2.DB.DBHelper;


public class App extends Application {
    public static App instance;
    private DBHelper database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DBHelper.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public DBHelper getDatabase() {
        return database;
    }
}
