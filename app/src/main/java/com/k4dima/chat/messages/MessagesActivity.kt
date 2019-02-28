package com.k4dima.chat.messages

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.k4dima.chat.R
import com.k4dima.chat.app.model.Message
import com.k4dima.chat.app.model.ResponseMessages
import com.k4dima.chat.app.model.ResponseMessages.ChatInputType.*
import com.k4dima.chat.app.model.User
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MessagesActivity : DaggerAppCompatActivity(), MessagesView {
    @Inject
    internal lateinit var presenter: MessagesPresenter
    @BindView(R.id.messagesList)
    internal lateinit var messagesList: MessagesList
    @BindView(R.id.input)
    internal lateinit var input: MessageInput
    @BindView(R.id.select)
    internal lateinit var select: LinearLayout
    private lateinit var adapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messages_activity)
        ButterKnife.bind(this)
        adapter = MessagesListAdapter(senderId, null)
        adapter.setLoadMoreListener { page, _ ->
            if (page == 1)
                presenter.savedMessages()
        }
        messagesList.setAdapter(adapter)
        input.setInputListener {
            send(it.toString())
            true
        }
        send(START)
    }

    override fun addMessagesToEnd(messages: List<Message>) = adapter.addToEnd(messages, true)

    override fun addMessages(responseMessages: ResponseMessages) {
        setInput(responseMessages)
        responseMessages.messages.forEach {
            addMessage(it)
        }
    }

    override fun addMessage(message: Message) = adapter.addToStart(message, true)

    private fun setInput(responseMessages: ResponseMessages) =
            when (responseMessages.chatInputType) {
                TEXT -> {
                    input.inputEditText.inputType = InputType.TYPE_CLASS_TEXT
                    select.visibility = View.GONE
                }
                NUMBER -> {
                    input.inputEditText.inputType = InputType.TYPE_CLASS_PHONE
                    select.visibility = View.GONE
                }
                SELECT -> {
                    input.inputEditText.inputType = InputType.TYPE_CLASS_TEXT
                    select.visibility = View.VISIBLE
                    val selects = responseMessages.selects
                    val length = selects!!.size
                    for (i in 0 until length) {
                        val button = select.getChildAt(i) as Button
                        button.text = selects[i]
                        button.setOnClickListener { v -> send((v as Button).text.toString()) }
                    }
                }
            }

    private fun send(text: String) =
            presenter.send(Message(User(senderId, "Me"), text))

    companion object {
        const val START = ":start"
        private const val senderId = "0"
    }
}