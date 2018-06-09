package com.lemonade.dima.repository;

import android.support.annotation.NonNull;

import com.lemonade.dima.model.Message;
import com.lemonade.dima.model.ResponseMessages;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface LemonadeRepository {
    @NonNull
    Observable<ResponseMessages> message(Message message);

    Single<List<Message>> saved();
}