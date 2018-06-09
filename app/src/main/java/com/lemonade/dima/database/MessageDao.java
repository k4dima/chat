package com.lemonade.dima.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lemonade.dima.model.Message;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    Single<List<Message>> getAll();

    @Insert
    void insert(Message employee);
}