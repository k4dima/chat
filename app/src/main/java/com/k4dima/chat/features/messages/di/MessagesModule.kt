package com.k4dima.chat.features.messages.di

import android.content.Context

import com.k4dima.chat.features.messages.MessagesActivity
import com.k4dima.chat.features.messages.MessagesView

import dagger.Binds
import dagger.Module

@Module
interface MessagesModule {
    @MessagesScope
    @Binds
    fun provideContext(messagesActivity: MessagesActivity): Context

    @MessagesScope
    @Binds
    fun provideMessagesView(messagesActivity: MessagesActivity): MessagesView
}