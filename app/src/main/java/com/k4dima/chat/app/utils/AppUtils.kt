package com.k4dima.chat.app.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

fun Any.toJson() = GsonBuilder().create().toJsonTree(this) as JsonObject