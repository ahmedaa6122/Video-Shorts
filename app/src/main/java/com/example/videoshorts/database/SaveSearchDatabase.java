package com.example.videoshorts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.videoshorts.model.SaveSearch;

@Database(entities = {SaveSearch.class}, version = 1)
public abstract class SaveSearchDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "historySearch.db";
    private static SaveSearchDatabase instance;

    public static synchronized SaveSearchDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), SaveSearchDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract SaveSearchDao saveSearchDao();
}
