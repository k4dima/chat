package com.k4dima.chat.messages.di

import android.content.Context

import com.k4dima.chat.messages.MessagesActivity
import com.k4dima.chat.messages.MessagesView

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