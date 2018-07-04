package com.k4dima.chat.core.api;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import com.k4dima.chat.core.di.messages.MessagesScope;

import java.security.SecureRandom;
import java.util.Random;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@MessagesScope
public class MockingInterceptor implements Interceptor {
    private final RequestsHandler handler;
    private final Random random = new SecureRandom();

    @Inject
    MockingInterceptor(RequestsHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(@NonNull Chain chain) {
        Request request = chain.request();
        Response response = handler.proceed(request);
        int stubDelay = 500 + random.nextInt(1500);
        SystemClock.sleep(stubDelay);
        return response;
    }
}