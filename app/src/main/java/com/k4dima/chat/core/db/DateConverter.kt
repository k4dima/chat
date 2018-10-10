package com.k4dima.chat.core.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToTimestamp(date: Date) = date.time

    @TypeConverter
    fun timestampToDate(timestamp: Long) = Date(timestamp)
}