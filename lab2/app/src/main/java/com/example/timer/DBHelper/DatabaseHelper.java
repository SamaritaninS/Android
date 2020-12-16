package com.example.timer.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import com.example.timer.Model.Timer;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "TimersDB";

    // Table name: Note.
    private static final String TABLE_NAME = "Timer";

    public static final String TIMER_ID = "_id";
    public static final String TIMER_NAME = "name";
    public static final String PREPARATION_TIME = "preparationTime";
    public static final String WARM_TIME = "warmTime";
    public static final String WORK_TIME = "workTime";
    public static final String RELAXATION_TIME = "relaxationTime";
    public static final String CYCLE_TIME = "cycleCount";
    public static final String SET_COUNT = "setCount";
    public static final String PAUSE_TIME = "pauseTime";
    public static final String COLOR = "color";

    public DatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script1 = "CREATE TABLE " + TABLE_NAME + "("
                + TIMER_ID + " INTEGER PRIMARY KEY,"
                + TIMER_NAME + " TEXT,"
                + PREPARATION_TIME + " TEXT,"
                + WARM_TIME + " TEXT,"
                + WORK_TIME + " TEXT,"
                + RELAXATION_TIME + " TEXT,"
                + CYCLE_TIME + " TEXT,"
                + SET_COUNT + " TEXT,"
                + PAUSE_TIME + " TEXT,"
                + COLOR + " TEXT" + ")";

        db.execSQL(script1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addTimer(Timer timer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIMER_NAME, timer.getTimerName());
        values.put(PREPARATION_TIME, timer.getPreparationTime());
        values.put(WARM_TIME, timer.getWarmTime());
        values.put(WORK_TIME, timer.getWorkTime());
        values.put(RELAXATION_TIME, timer.getRelaxationTime());
        values.put(CYCLE_TIME, timer.getCycleCount());
        values.put(SET_COUNT, timer.getSetCount());
        values.put(PAUSE_TIME, timer.getPauseTime());
        values.put(COLOR, timer.getColor());
        // Inserting Row
        db.insert(TABLE_NAME, null, values);

        // Closing database connection
        db.close();
    }

    public int getTimerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Timer getTimer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { TIMER_ID,
                        TIMER_NAME, PREPARATION_TIME , WARM_TIME,
                        WORK_TIME, RELAXATION_TIME, CYCLE_TIME,
                        SET_COUNT, PAUSE_TIME, COLOR}, TIMER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Timer timer = new Timer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8), cursor.getString(9));
        // return note
        return timer;
    }

    public void deleteTimers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + TIMER_ID + " INTEGER PRIMARY KEY,"
                + TIMER_NAME + " TEXT,"
                + PREPARATION_TIME + " TEXT,"
                + WARM_TIME + " TEXT,"
                + WORK_TIME + " TEXT,"
                + RELAXATION_TIME + " TEXT,"
                + CYCLE_TIME + " TEXT,"
                + SET_COUNT + " TEXT,"
                + PAUSE_TIME + " TEXT,"
                + COLOR + " TEXT" + ")";
        db.execSQL(script);
    }

    public List<Timer> getAllTimer() {
        List<Timer> timerList = new ArrayList<Timer>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Timer timer = new Timer();
                timer.setTimerId(Integer.parseInt(cursor.getString(0)));
                timer.setTimerName(cursor.getString(1));
                timer.setPreparationTime(cursor.getString(2));
                timer.setWarmTime(cursor.getString(3));
                timer.setWorkTime(cursor.getString(4));
                timer.setRelaxationTime(cursor.getString(5));
                timer.setCycleCount(cursor.getString(6));
                timer.setSetCount(cursor.getString(7));
                timer.setPauseTime(cursor.getString(8));
                timer.setColor(cursor.getString(9));
                // Adding note to list
                timerList.add(timer);
            } while (cursor.moveToNext());
        }

        // return note list
        return timerList;
    }
    

    public int updateTimer(Timer timer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TIMER_NAME, timer.getTimerName());
        values.put(PREPARATION_TIME, timer.getPreparationTime());
        values.put(WARM_TIME, timer.getWarmTime());
        values.put(WORK_TIME, timer.getWorkTime());
        values.put(RELAXATION_TIME, timer.getRelaxationTime());
        values.put(CYCLE_TIME, timer.getCycleCount());
        values.put(SET_COUNT, timer.getSetCount());
        values.put(PAUSE_TIME, timer.getPauseTime());
        values.put(COLOR, timer.getColor());

        // updating row
        return db.update(TABLE_NAME, values, TIMER_ID + " = ?",
                new String[]{String.valueOf(timer.getTimerId())});
    }

    public void deleteTimer(Timer timer) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TIMER_ID + " = ?",
                new String[] { String.valueOf(timer.getTimerId()) });
        db.close();
    }
}