package com.k4dima.chat.core.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.k4dima.chat.core.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}