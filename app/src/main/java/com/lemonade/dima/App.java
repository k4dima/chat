package com.lemonade.dima;

import android.app.Activity;
import android.app.Application;

import com.lemonade.dima.di.ApiFactory;
import com.lemonade.dima.di.ApplicationComponent;
import com.lemonade.dima.di.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {
    public static ApplicationComponent applicationComponent;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.create();
        ApiFactory.recreate(); // todo
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}