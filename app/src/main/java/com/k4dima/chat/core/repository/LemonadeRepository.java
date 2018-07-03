package com.k4dima.chat.core.repository;

import android.support.annotation.NonNull;

import com.k4dima.chat.core.model.Message;
import com.k4dima.chat.core.model.ResponseMessages;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface LemonadeRepository {
    @NonNull
    Observable<ResponseMessages> message(Message message);

    Single<List<Message>> saved();
}