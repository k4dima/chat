package com.k4dima.chat.app.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.k4dima.chat.R
import com.k4dima.chat.app.model.Message
import com.k4dima.chat.app.model.ResponseMessages
import com.k4dima.chat.app.utils.toJson
import com.k4dima.chat.app.model.User
import com.k4dima.chat.messages.MessagesActivity.Companion.START
import com.k4dima.chat.messages.di.MessagesScope
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*
import javax.inject.Inject

@MessagesScope
internal class RequestsHandler
@Inject
constructor(private val context: Context) {
    private fun message(id: Int) = responseMessage(string(id))

    private fun string(id: Int) = context.getString(id)

    fun proceed(request: Request): Response {
        val inputMessageText = inputMessage(request).text
        val no = "NO"
        val restart = "RESTART"
        if (inputMessageText == START)
            step = 1
        else if (step == 3 && inputMessageText == no)
            step = 5
        else if (step == 4 && inputMessageText == restart)
            step = 1
        else
            step++
        val messages = ArrayList<Message>()
        var chatInputType: ResponseMessages.ChatInputType? = null
        var selects: Array<String>? = null
        when (step) {
            1 -> {
                messages.add(message(R.string.hello))
                messages.add(message(R.string.name))
                chatInputType = ResponseMessages.ChatInputType.TEXT
            }
            2 -> {
                messages.add(responseMessage(context.getString(R.string.meet, inputMessageText)))
                messages.add(message(R.string.number))
                chatInputType = ResponseMessages.ChatInputType.NUMBER
            }
            3 -> {
                messages.add(message(R.string.agree))
                chatInputType = ResponseMessages.ChatInputType.SELECT
                selects = arrayOf(no, string(R.string.yes))
            }
            4 -> {
                messages.add(message(R.string.thanks))
                messages.add(message(R.string.last))
                messages.add(message(R.string.todo))
                chatInputType = ResponseMessages.ChatInputType.SELECT
                selects = arrayOf(restart, string(R.string.exit))
            }
            5 -> {
                messages.add(message(R.string.bye))
                chatInputType = ResponseMessages.ChatInputType.TEXT
                step = 0
            }
        }
        val responseMessages = ResponseMessages(messages, chatInputType!!, selects)
        val jsonObject = responseMessages.toJson()
        val jsonObjectAsString = jsonObject.toString()
        try {
            ByteArrayInputStream(jsonObjectAsString.toByteArray()).use { stream ->
                return OkHttpResponse.success(request, stream)
            }
        } catch (e: IOException) {
            return OkHttpResponse.error(request, e.message!!)
        }

    }

    companion object {
        private var step = 0

        private fun inputMessage(request: Request): Message {
            val bufferedSink = Buffer()
            try {
                request.body()!!.writeTo(bufferedSink)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val inputStream = bufferedSink.buffer().inputStream()
            val string = convertStreamToString(inputStream)
            val builder = GsonBuilder()
            val gson = builder.create()
            return gson.fromJson(string, Message::class.java)
        }

        private fun convertStreamToString(stream: InputStream): String {
            val s = Scanner(stream).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }

        private fun responseMessage(text: String) = Message(User("" + 1, "Server"), text)
    }
}