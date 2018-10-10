package com.k4dima.chat.features.messages.repository

import androidx.room.Room
import android.content.Context
import com.k4dima.chat.core.db.AppDatabase
import com.k4dima.chat.features.messages.di.MessagesScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryProvider {
    @MessagesScope
    @Binds
    abstract fun provideLemonadeRepository(repository: DefaultLemonadeRepository): LemonadeRepository

    @Module
    companion object {
        @JvmStatic
        @MessagesScope
        @Provides
        internal fun provideMessageDao(context: Context) = Room
                .databaseBuilder(context, AppDatabase::class.java, "database")
                .build()
                .messageDao()
    }
}