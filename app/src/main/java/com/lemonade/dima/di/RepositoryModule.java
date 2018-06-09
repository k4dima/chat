package com.lemonade.dima.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.lemonade.dima.database.AppDatabase;
import com.lemonade.dima.database.MessageDao;
import com.lemonade.dima.repository.DefaultLemonadeRepository;
import com.lemonade.dima.repository.LemonadeRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {
    @Provides
    @Singleton
    static MessageDao provideMessageDao(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .build().messageDao();
    }

    //@Provides
    @Binds
    public abstract LemonadeRepository provideLemonadeRepository(DefaultLemonadeRepository repository);
}