package com.k4dima.chat.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.k4dima.chat.core.model.Message

import io.reactivex.Single

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun all(): Single<MutableList<Message>>

    @Insert
    fun insert(employee: Message)
}