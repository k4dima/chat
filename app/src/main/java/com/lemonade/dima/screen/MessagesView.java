package com.lemonade.dima.screen;

import com.lemonade.dima.model.Message;
import com.lemonade.dima.model.ResponseMessages;

import java.util.List;

public interface MessagesView {
    void addMessagesToEnd(List<Message> message);

    void addMessage(Message message);

    void addMessages(ResponseMessages responseMessages);
}