package com.k4dima.chat.app.api

import android.os.SystemClock
import com.k4dima.chat.messages.di.MessagesScope
import okhttp3.Interceptor
import java.security.SecureRandom
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

@MessagesScope
class MockingInterceptor
@Inject
internal constructor(private val handler: RequestsHandler) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
            (SECONDS.toMillis(1) / 2)
                    .run { SystemClock.sleep(this + SecureRandom().nextInt(toInt())) }
                    .run { handler.proceed(chain.request()) }
}