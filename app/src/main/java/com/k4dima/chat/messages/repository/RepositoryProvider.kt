package com.k4dima.chat.messages.repository

import android.content.Context
import androidx.room.Room
import com.k4dima.chat.app.db.AppDatabase
import com.k4dima.chat.messages.di.MessagesScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RepositoryProvider {
    @MessagesScope
    @Binds
    abstract fun provideLemonadeRepository(repository: DefaultLemonadeRepository):
            LemonadeRepository

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