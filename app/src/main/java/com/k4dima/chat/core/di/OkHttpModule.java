package com.k4dima.chat.core.di;

import android.support.annotation.NonNull;

import com.k4dima.chat.core.api.MockingInterceptor;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
abstract class OkHttpModule {
    @NonNull
    @Singleton
    @Provides
    static OkHttpClient buildClient(Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @NonNull
    @Singleton
    @Binds
    abstract Interceptor create(MockingInterceptor mockingInterceptor);
}