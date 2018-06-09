package com.lemonade.dima.repository;

import android.support.annotation.NonNull;

import com.lemonade.dima.di.ApiFactory;
import com.lemonade.dima.model.Message;
import com.lemonade.dima.database.MessageDao;
import com.lemonade.dima.model.ResponseMessages;
import com.lemonade.dima.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.lemonade.dima.screen.MessagesActivity.START;

@Singleton
public class DefaultLemonadeRepository implements LemonadeRepository {
    private MessageDao messageDao;

    @Inject
    public DefaultLemonadeRepository(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    @NonNull
    public Observable<ResponseMessages> message(Message message) {
        return ApiFactory.getLemonadeService()
                .message(AppUtils.toJson(message))
                .doOnNext(responseMessages -> {
                    if (!message.getText().equals(START))
                        messageDao.insert(message);
                    for (Message message2 : responseMessages.getMessages())
                        messageDao.insert(message2);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Message>> saved() {
        return messageDao
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}