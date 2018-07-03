package com.k4dima.chat.core.api;

import com.k4dima.chat.core.model.ResponseMessages;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LemonadeService {
    @POST("/message")
    Observable<ResponseMessages> message(@Body JsonObject message);
}