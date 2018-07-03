package com.k4dima.chat.core.di;

import dagger.Component;

/*@Singleton*/
@SuppressWarnings("WeakerAccess")
@Component(modules = {/*AndroidInjectionModule.class*//*, AppModule.class*/})
public interface ApplicationComponent /*extends AndroidInjector<App>*/ {
    MessagesActivityComponent plusMessagesActivityComponent(MessagesActivityModule module);
}