package com.k4dima.chat.core.api

import com.google.gson.JsonObject
import com.k4dima.chat.core.model.ResponseMessages
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LemonadeService {
    @POST("/message")
    fun message(@Body message: JsonObject): Single<ResponseMessages>
}