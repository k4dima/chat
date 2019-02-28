package com.k4dima.chat.messages

import com.k4dima.chat.app.model.Message
import com.k4dima.chat.app.model.ResponseMessages

interface MessagesView {
    fun addMessagesToEnd(messages: List<Message>)

    fun addMessage(message: Message)

    fun addMessages(responseMessages: ResponseMessages)
}