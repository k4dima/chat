package com.lemonade.dima.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lemonade.dima.model.Message;

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
}