package com.k4dima.chat.core.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.k4dima.chat.core.model.Message;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message")
    Single<List<Message>> getAll();

    @Insert
    void insert(Message employee);
}