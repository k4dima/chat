package com.k4dima.chat.features.messages

import com.k4dima.chat.core.model.Message
import com.k4dima.chat.features.messages.MessagesActivity.Companion.START
import com.k4dima.chat.features.messages.repository.LemonadeRepository
import javax.inject.Inject

class MessagesPresenter
@Inject
internal constructor(private val messagesView: MessagesView, private val repository: LemonadeRepository) {
    fun send(message: Message) {
        if (message.text != START) messagesView.addMessage(message)
        repository
                .message(message)
                .subscribe(messagesView::addMessages, Throwable::printStackTrace)
    }

    fun savedMessages() {
        repository
                .saved()
                .subscribe { messages ->
                    for (i in 0..1) messages.removeAt(messages.size - 1)
                    if (messages.isNotEmpty()) messagesView.addMessagesToEnd(messages)
                }
    }
}