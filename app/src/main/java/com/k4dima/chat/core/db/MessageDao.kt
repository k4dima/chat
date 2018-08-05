package com.k4dima.chat.core.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

import com.k4dima.chat.core.model.Message

import io.reactivex.Single

@Dao
interface MessageDao {
    @Query("SELECT * FROM message")
    fun all(): Single<MutableList<Message>>

    @Insert
    fun insert(employee: Message)
}