package com.k4dima.chat.core.api

import android.os.SystemClock
import com.k4dima.chat.features.messages.di.MessagesScope
import okhttp3.Interceptor
import okhttp3.Response
import java.security.SecureRandom
import javax.inject.Inject

@MessagesScope
class MockingInterceptor @Inject
internal constructor(private val handler: RequestsHandler) : Interceptor {
    private val random = SecureRandom()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = handler.proceed(request)
        val stubDelay = 500 + random.nextInt(1000)
        SystemClock.sleep(stubDelay.toLong())
        return response
    }
}