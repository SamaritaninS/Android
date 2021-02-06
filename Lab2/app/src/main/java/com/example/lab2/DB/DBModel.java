package com.example.lab2.DB;

import androidx.room.PrimaryKey;

public class DBModel {
    @PrimaryKey(autoGenerate = true)
    public int Id;
    public String Name;
    public int Color;
    public int Preparation;
    public int Training;
    public int Relax;
    public int Cycles;
}
