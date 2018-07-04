package com.k4dima.chat.features.messages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.k4dima.chat.App;
import com.k4dima.chat.R;
import com.k4dima.chat.core.di.messages.MessagesComponent;
import com.k4dima.chat.core.di.messages.MessagesModule;
import com.k4dima.chat.core.model.Message;
import com.k4dima.chat.core.model.ResponseMessages;
import com.k4dima.chat.core.model.User;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesActivity extends AppCompatActivity implements MessagesView {
    public static final String START = ":start";
    private static final String senderId = "0";
    @Inject
    MessagesPresenter presenter;
    int selectionCount;
    @BindView(R.id.messagesList)
    MessagesList messagesList;
    @BindView(R.id.input)
    MessageInput input;
    @BindView(R.id.select)
    LinearLayout select;
    private MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        //AndroidInjection.inject(this);
        MessagesComponent messagesComponent =
                App.applicationComponent.plusMessagesActivityComponent(new MessagesModule(this));
        messagesComponent.inject(this);
        ButterKnife.bind(this);
        adapter = new MessagesListAdapter<>(senderId, null);
        adapter.setLoadMoreListener((page, totalItemsCount) -> {
            if (page == 1)
                presenter.savedMessages();
        });
        messagesList.setAdapter(adapter);
        input.setInputListener(input -> {
            send(input.toString());
            return true;
        });
        send(START);
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0)
            super.onBackPressed();
        else
            adapter.unselectAllItems();
    }

    @Override
    public void addMessagesToEnd(List<Message> messages) {
        adapter.addToEnd(messages, true);
    }

    @Override
    public void addMessages(ResponseMessages responseMessages) {
        setInput(responseMessages);
        for (Message message : responseMessages.getMessages())
            addMessage(message);
    }

    public void addMessage(Message message) {
        adapter.addToStart(message, true);
    }

    private void setInput(@NotNull ResponseMessages responseMessages) {
        ResponseMessages.ChatInputType chatInputType = responseMessages.getChatInputType();
        switch (chatInputType) {
            case TEXT:
                input.getInputEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                select.setVisibility(View.GONE);
                break;
            case NUMBER:
                input.getInputEditText().setInputType(InputType.TYPE_CLASS_PHONE);
                select.setVisibility(View.GONE);
                break;
            case SELECT:
                input.getInputEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                select.setVisibility(View.VISIBLE);
                String[] selects = responseMessages.getSelects();
                int length = selects.length;
                for (int i = 0; i < length; i++) {
                    Button button = (Button) select.getChildAt(i);
                    button.setText(selects[i]);
                    button.setOnClickListener(v -> send(((Button) v).getText().toString()));
                }
                break;
        }
    }

    private void send(String text) {
        presenter.send(new Message(new User(senderId, "Me"), text));
    }
}