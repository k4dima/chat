package com.k4dima.chat.core.model

import com.stfalcon.chatkit.commons.models.IUser

class User(private val id: String, private val name: String) : IUser {
    override fun getId() = id

    override fun getName() = name

    override fun getAvatar() = null
}