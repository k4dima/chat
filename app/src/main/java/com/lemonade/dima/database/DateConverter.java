package com.lemonade.dima.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public Date  timestampToDate(Long timestamp) {
        return new Date(timestamp);
    }
}