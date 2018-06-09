package com.lemonade.dima.api;

import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.security.SecureRandom;
import java.util.Random;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MockingInterceptor implements Interceptor {
    private final RequestsHandler mHandlers;
    private final Random mRandom;

    private MockingInterceptor() {
        mHandlers = new RequestsHandler();
        mRandom = new SecureRandom();
    }

    @NonNull
    public static Interceptor create() {
        return new MockingInterceptor();
    }

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response response = mHandlers.proceed(request);
        int stubDelay = 500 + mRandom.nextInt(1500);
        SystemClock.sleep(stubDelay);
        return response;
    }
}