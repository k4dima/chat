package com.lemonade.dima.api;

import com.lemonade.dima.model.ResponseMessages;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LemonadeService {
    @POST("/message")
    Observable<ResponseMessages> message(@Body JsonObject message);
}