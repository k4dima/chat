package com.k4dima.chat.core.di;

import android.support.annotation.NonNull;

import com.k4dima.chat.core.api.LemonadeService;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
final class ApiModule {
    @NonNull
    @Singleton
    @Provides
    static LemonadeService lemonadeService(@NotNull Retrofit retrofit) {
        return retrofit.create(LemonadeService.class);
    }

    @NonNull
    @Singleton
    @Provides
    static Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://api.lemonade.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}