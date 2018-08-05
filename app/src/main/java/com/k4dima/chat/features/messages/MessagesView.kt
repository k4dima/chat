package com.k4dima.chat.features.messages

import com.k4dima.chat.core.model.Message
import com.k4dima.chat.core.model.ResponseMessages

interface MessagesView {
    fun addMessagesToEnd(messages: List<Message>)

    fun addMessage(message: Message)

    fun addMessages(responseMessages: ResponseMessages)
}