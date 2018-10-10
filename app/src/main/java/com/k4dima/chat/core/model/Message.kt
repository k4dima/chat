package com.k4dima.chat.core.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.k4dima.chat.core.db.DateConverter
import com.stfalcon.chatkit.commons.models.IMessage
import java.util.*

@Entity
class Message private constructor(
        @Embedded(prefix = "user")
        private val user: User,
        private val text: String,
        @field:TypeConverters(DateConverter::class)
        private var createdAt: Date
) : IMessage {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(user: User, text: String) : this(user, text, Date())

    override fun getId(): String {
        return "" + id
    }

    override fun getText(): String {
        return text
    }

    override fun getCreatedAt(): Date {
        return createdAt
    }

    fun setCreatedAt(createdAt: Date) {
        this.createdAt = createdAt
    }

    override fun getUser(): User {
        return this.user
    }
}