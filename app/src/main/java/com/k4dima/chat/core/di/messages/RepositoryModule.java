package com.k4dima.chat.core.di.messages;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.k4dima.chat.core.db.AppDatabase;
import com.k4dima.chat.core.db.MessageDao;
import com.k4dima.chat.core.repository.DefaultLemonadeRepository;
import com.k4dima.chat.core.repository.LemonadeRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {
    @MessagesScope
    @Provides
    static MessageDao provideMessageDao(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .build().messageDao();
    }

    @MessagesScope
    @Binds
    public abstract LemonadeRepository provideLemonadeRepository(DefaultLemonadeRepository repository);
}