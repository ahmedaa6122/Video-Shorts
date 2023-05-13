package com.example.videoshorts.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.videoshorts.model.SaveSearch;

import java.util.List;

@Dao
public interface SaveSearchDao {
    @Insert
    void insertData(SaveSearch key);

    @Query("SELECT * FROM listSearch")
    List<SaveSearch> getAll();

    @Delete
    void deleteHistorySearch(SaveSearch object);
}
