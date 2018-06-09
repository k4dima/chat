package com.lemonade.dima.di;

import android.content.Context;

import com.lemonade.dima.screen.MessagesActivity;
import com.lemonade.dima.screen.MessagesView;

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

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    MessagesView provideMessagesView() {
        return messageView;
    }
    /*@ContributesAndroidInjector
    abstract MessagesActivity contributeActivityInjector();*/
}