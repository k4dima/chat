package com.k4dima.chat.messages.repository

import com.k4dima.chat.app.model.Message
import com.k4dima.chat.app.model.ResponseMessages

import io.reactivex.Single

interface LemonadeRepository {
    fun message(message: Message): Single<ResponseMessages>

    fun saved(): Single<MutableList<Message>>
}