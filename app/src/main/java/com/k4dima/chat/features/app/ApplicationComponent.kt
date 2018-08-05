package com.k4dima.chat.features.app

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppBindingModule::class])
internal interface ApplicationComponent : AndroidInjector<App>