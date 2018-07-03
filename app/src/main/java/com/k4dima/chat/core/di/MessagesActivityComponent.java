package com.k4dima.chat.core.di;

import com.k4dima.chat.features.messages.MessagesActivity;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {MessagesActivityModule.class, RepositoryModule.class, ApiModule.class,
        OkHttpModule.class})
public interface MessagesActivityComponent {
    void inject(MessagesActivity messagesActivity);
    //@Subcomponent.Builder
    /*interface Builder {
        MessagesActivityComponent build();

        @BindsInstance
        Builder activity(MessagesActivity messagesActivity);
    }*/
}