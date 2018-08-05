package com.k4dima.chat.features.app

import com.k4dima.chat.features.messages.MessagesActivity
import com.k4dima.chat.features.messages.api.ApiFactory
import com.k4dima.chat.features.messages.api.OkHttpProvider
import com.k4dima.chat.features.messages.di.MessagesModule
import com.k4dima.chat.features.messages.di.MessagesScope
import com.k4dima.chat.features.messages.repository.RepositoryProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class AppBindingModule {
    @MessagesScope
    @ContributesAndroidInjector(modules = [MessagesModule::class, RepositoryProvider::class, ApiFactory::class,
        OkHttpProvider::class])
    internal abstract fun messagesActivity(): MessagesActivity
}