package com.k4dima.chat.core.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.k4dima.chat.core.api.LemonadeService;
import com.k4dima.chat.core.db.AppDatabase;
import com.k4dima.chat.core.db.MessageDao;
import com.k4dima.chat.core.repository.DefaultLemonadeRepository;
import com.k4dima.chat.core.repository.LemonadeRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {
    @Singleton
    @Provides
    static MessageDao provideMessageDao(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .build().messageDao();
    }

    @Singleton
    @Binds
    public abstract LemonadeRepository provideLemonadeRepository(DefaultLemonadeRepository repository);
}