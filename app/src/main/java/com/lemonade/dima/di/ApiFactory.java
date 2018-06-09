package com.lemonade.dima.di;

import android.support.annotation.NonNull;

import com.lemonade.dima.api.LemonadeService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {
    private static volatile LemonadeService sService;

    @NonNull
    public static LemonadeService getLemonadeService() {
        LemonadeService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(LemonadeService.class);
                }
            }
        }
        return service;
    }

    public static void recreate() {
        OkHttpProvider.recreate();
        sService = buildRetrofit().create(LemonadeService.class);
    }

    @NonNull
    private static Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.lemonade.com/")
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}