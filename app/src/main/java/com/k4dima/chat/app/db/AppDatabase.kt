package com.k4dima.chat.app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.k4dima.chat.app.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}