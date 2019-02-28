package com.k4dima.chat.app.api

import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.io.InputStream

internal object OkHttpResponse {
    private val APPLICATION_JSON = MediaType.parse("application/json")

    @Throws(IOException::class)
    fun success(request: Request, stream: InputStream): Response = Buffer()
            .readFrom(stream)
            .run {
                Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(ResponseBody.create(APPLICATION_JSON, this.size(), this))
                        .build()
            }

    fun error(request: Request, message: String): Response = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(500)
            .message(message)
            .body(ResponseBody.create(APPLICATION_JSON, ByteArray(0)))
            .build()
}