package com.k4dima.chat.features.messages.repository

import com.k4dima.chat.core.api.LemonadeService
import com.k4dima.chat.core.db.MessageDao
import com.k4dima.chat.core.model.Message
import com.k4dima.chat.core.utils.AppUtils.toJson
import com.k4dima.chat.features.messages.MessagesActivity.Companion.START
import com.k4dima.chat.features.messages.di.MessagesScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@MessagesScope
class DefaultLemonadeRepository
@Inject
internal constructor(private val messageDao: MessageDao, private val lemonadeService: LemonadeService)
    : LemonadeRepository {
    override fun message(message: Message) =
            lemonadeService.message(message.toJson())
                    .doOnSuccess {
                        if (message.text != START) messageDao.insert(message)
                        for (message2 in it.messages) messageDao.insert(message2)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!

    override fun saved() =
            messageDao.all()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())!!
}