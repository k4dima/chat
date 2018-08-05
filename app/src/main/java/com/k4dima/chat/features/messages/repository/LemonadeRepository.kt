package com.k4dima.chat.features.messages.repository

import com.k4dima.chat.core.model.Message
import com.k4dima.chat.core.model.ResponseMessages

import io.reactivex.Single

interface LemonadeRepository {
    fun message(message: Message): Single<ResponseMessages>

    fun saved(): Single<MutableList<Message>>
}