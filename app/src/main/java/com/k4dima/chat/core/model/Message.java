package com.k4dima.chat.core.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.k4dima.chat.core.db.DateConverter;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

@Entity
public class Message implements IMessage {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String text;
    @TypeConverters({DateConverter.class})
    private Date createdAt;
    @Embedded(prefix = "user")
    private User user;

    public Message(User user, String text) {
        this(user, text, new Date());
    }

    private Message(User user, String text, Date createdAt) {
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    @Override
    public String getId() {
        return "" + id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public User getUser() {
        return this.user;
    }
}