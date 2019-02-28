package com.k4dima.chat.app.model

class ResponseMessages(val messages: List<Message>,
                       val chatInputType: ChatInputType,
                       val selects: Array<String>?) {
    enum class ChatInputType {
        TEXT, NUMBER, SELECT
    }
}