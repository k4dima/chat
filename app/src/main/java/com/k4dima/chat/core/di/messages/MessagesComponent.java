package com.k4dima.chat.core.di.messages;

import com.k4dima.chat.features.messages.MessagesActivity;

import dagger.Subcomponent;

@MessagesScope
@Subcomponent(modules = {MessagesModule.class, RepositoryModule.class, ApiModule.class,
        OkHttpModule.class})
public interface MessagesComponent {
    void inject(MessagesActivity messagesActivity);
    //@Subcomponent.Builder
    /*interface Builder {
        MessagesComponent build();

        @BindsInstance
        Builder activity(MessagesActivity messagesActivity);
    }*/
}