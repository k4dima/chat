package com.k4dima.chat.app.api

import com.google.gson.JsonObject
import com.k4dima.chat.app.model.ResponseMessages
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LemonadeService {
    @POST("/message")
    fun message(@Body message: JsonObject): Single<ResponseMessages>
}