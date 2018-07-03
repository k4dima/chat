package com.k4dima.chat.features.messages;

import com.k4dima.chat.core.model.Message;
import com.k4dima.chat.core.model.ResponseMessages;

import java.util.List;

public interface MessagesView {
    void addMessagesToEnd(List<Message> message);

    void addMessage(Message message);

    void addMessages(ResponseMessages responseMessages);
}