package com.k4dima.chat.messages

import com.k4dima.chat.app.model.Message
import com.k4dima.chat.messages.MessagesActivity.Companion.START
import com.k4dima.chat.messages.repository.LemonadeRepository
import javax.inject.Inject

class MessagesPresenter
@Inject
internal constructor(private val messagesView: MessagesView,
                     private val repository: LemonadeRepository) {
    fun send(message: Message) {
        if (message.text != START)
            messagesView.addMessage(message)
        repository.message(message)
                .subscribe(messagesView::addMessages, Throwable::printStackTrace)
    }

    fun savedMessages() = repository.saved()
            .subscribe { messages ->
                messages.dropLast(2).run {
                    if (isNotEmpty())
                        messagesView.addMessagesToEnd(this)
                }
            }!!
}