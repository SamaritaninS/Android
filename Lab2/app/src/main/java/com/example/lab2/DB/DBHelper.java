package com.example.lab2.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DBModel.class}, version = 1)
public abstract class DBHelper extends RoomDatabase {
    public abstract DBTimer dbTimer();
}