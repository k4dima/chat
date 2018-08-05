package com.k4dima.chat.core.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

object AppUtils {
    fun Any.toJson() = GsonBuilder().create().toJsonTree(this) as JsonObject
}