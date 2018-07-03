package com.k4dima.chat.core.di;

import android.content.Context;

import com.k4dima.chat.features.messages.MessagesActivity;
import com.k4dima.chat.features.messages.MessagesView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MessagesActivityModule {
    private final MessagesView messageView;
    private final Context context;

    public MessagesActivityModule(MessagesActivity messagesActivity) {
        messageView = messagesActivity;
        context = messagesActivity;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    MessagesView provideMessagesView() {
        return messageView;
    }
    /*@ContributesAndroidInjector
    abstract MessagesActivity contributeActivityInjector();*/
}