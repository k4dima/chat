package com.k4dima.chat.core.di.app;

import com.k4dima.chat.core.di.messages.MessagesComponent;
import com.k4dima.chat.core.di.messages.MessagesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {/*AndroidInjectionModule.class,*/ AppModule.class})
public interface ApplicationComponent /*extends AndroidInjector<App>*/ {
    MessagesComponent plusMessagesActivityComponent(MessagesModule module);
}