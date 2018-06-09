package com.lemonade.dima.di;

import com.lemonade.dima.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/*@Singleton*/
@SuppressWarnings("WeakerAccess")
@Component(modules = {/*AndroidInjectionModule.class*//*, AppModule.class*/})
public interface ApplicationComponent /*extends AndroidInjector<App>*/ {
    MessagesActivityComponent plusMessagesActivityComponent(MessagesActivityModule module);
}