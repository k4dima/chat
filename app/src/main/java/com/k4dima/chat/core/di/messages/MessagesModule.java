package com.k4dima.chat.core.di.messages;

import android.content.Context;

import com.k4dima.chat.features.messages.MessagesActivity;
import com.k4dima.chat.features.messages.MessagesView;

import dagger.Module;
import dagger.Provides;

@Module
public class MessagesModule {
    private final MessagesView messageView;
    private final Context context;

    public MessagesModule(MessagesActivity messagesActivity) {
        messageView = messagesActivity;
        context = messagesActivity;
    }

    @MessagesScope
    @Provides
    Context provideContext() {
        return context;
    }

    @MessagesScope
    @Provides
    MessagesView provideMessagesView() {
        return messageView;
    }
    /*@ContributesAndroidInjector
    abstract MessagesActivity contributeActivityInjector();*/
}