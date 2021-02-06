package com.example.lab2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBTimer {
    @Query("SELECT * FROM dbModel")
    List<DBModel> getAll();

    @Query("SELECT * FROM dbModel WHERE id = :id")
    DBModel getById(long id);

    @Query("SELECT * FROM dbModel WHERE name = :name")
    DBModel getByName(String name);

    @Insert
    void insert(DBModel dbModel);

    @Update
    void update(DBModel dbModel);

    @Delete
    void delete(DBModel dbModel);

    @Query("DELETE FROM dbModel")
    void DeleteAll();
}