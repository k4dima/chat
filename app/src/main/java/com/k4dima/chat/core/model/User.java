package com.k4dima.chat.core.model;

import com.stfalcon.chatkit.commons.models.IUser;

public class User implements IUser {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return null;
    }
}