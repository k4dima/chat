package com.k4dima.chat.core.repository;

import android.support.annotation.NonNull;

import com.k4dima.chat.core.api.LemonadeService;
import com.k4dima.chat.core.db.MessageDao;
import com.k4dima.chat.core.model.Message;
import com.k4dima.chat.core.model.ResponseMessages;
import com.k4dima.chat.core.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.k4dima.chat.features.messages.MessagesActivity.START;

@Singleton
public class DefaultLemonadeRepository implements LemonadeRepository {
    private MessageDao messageDao;
    private LemonadeService lemonadeService;

    @Inject
    DefaultLemonadeRepository(MessageDao messageDao, LemonadeService lemonadeService) {
        this.messageDao = messageDao;
        this.lemonadeService = lemonadeService;
    }

    @Override
    @NonNull
    public Observable<ResponseMessages> message(Message message) {
        return lemonadeService
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