package com.k4dima.chat.app

import com.k4dima.chat.messages.MessagesActivity
import com.k4dima.chat.messages.api.ApiFactory
import com.k4dima.chat.messages.api.OkHttpProvider
import com.k4dima.chat.messages.di.MessagesModule
import com.k4dima.chat.messages.di.MessagesScope
import com.k4dima.chat.messages.repository.RepositoryProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppBindingModule {
    @MessagesScope
    @ContributesAndroidInjector(modules =
    [MessagesModule::class, RepositoryProvider::class, ApiFactory::class, OkHttpProvider::class])
    internal abstract fun messagesActivity(): MessagesActivity
}