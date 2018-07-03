package com.k4dima.chat.core.di

import com.k4dima.chat.core.api.LemonadeService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal object ApiModule {
    @JvmStatic
    @Singleton
    @Provides
    fun lemonadeService(retrofit: Retrofit) =
            retrofit.create(LemonadeService::class.java)!!

    @JvmStatic
    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl("https://api.lemonade.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()!!
}