package com.k4dima.chat.features.messages.api

import com.k4dima.chat.core.api.MockingInterceptor
import com.k4dima.chat.features.messages.di.MessagesScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
internal abstract class OkHttpProvider {
    @MessagesScope
    @Binds
    internal abstract fun create(mockingInterceptor: MockingInterceptor): Interceptor

    @Module
    companion object {
        @JvmStatic
        @MessagesScope
        @Provides
        fun buildClient(interceptor: Interceptor): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
        }
    }
}