package com.k4dima.chat.core.model;

import java.util.List;

public class ResponseMessages {
    private final String[] selects;
    private List<Message> messages;
    private ChatInputType chatInputType;

    public ResponseMessages(List<Message> messages, ChatInputType chatInputType, String[] selects) {
        this.messages = messages;
        this.chatInputType = chatInputType;
        this.selects = selects;
    }

    public ChatInputType getChatInputType() {
        return chatInputType;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String[] getSelects() {
        return selects;
    }

    public enum ChatInputType {
        TEXT, NUMBER, SELECT
    }
}