package com.lemonade.dima.di;

import com.lemonade.dima.screen.MessagesActivity;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = {MessagesActivityModule.class, RepositoryModule.class})
public interface MessagesActivityComponent {
    void inject(MessagesActivity messagesActivity);
    //@Subcomponent.Builder
    /*interface Builder {
        MessagesActivityComponent build();

        @BindsInstance
        Builder activity(MessagesActivity messagesActivity);
    }*/
}