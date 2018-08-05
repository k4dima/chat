package com.k4dima.chat.features.messages.api

import com.k4dima.chat.core.api.LemonadeService
import com.k4dima.chat.features.messages.di.MessagesScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
internal object ApiFactory {
    @JvmStatic
    @MessagesScope
    @Provides
    fun lemonadeService(retrofit: Retrofit) =
            retrofit.create(LemonadeService::class.java)!!

    @JvmStatic
    @MessagesScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl("https://api.lemonade.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()!!
}