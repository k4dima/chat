package com.k4dima.chat.core.api;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

final class OkHttpResponse {
    private static final byte[] EMPTY_BODY = new byte[0];
    private static final MediaType APPLICATION_JSON = MediaType.parse("application/json");

    @NonNull
    static Response success(@NonNull Request request, @NonNull InputStream stream) throws IOException {
        Buffer buffer = new Buffer().readFrom(stream);
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(APPLICATION_JSON, buffer.size(), buffer))
                .build();
    }

    @NonNull
    static Response error(@NonNull Request request, @NonNull String message) {
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message(message)
                .body(ResponseBody.create(APPLICATION_JSON, EMPTY_BODY))
                .build();
    }
}