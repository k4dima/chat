package com.lemonade.dima.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lemonade.dima.App;
import com.lemonade.dima.R;
import com.lemonade.dima.di.MessagesActivityComponent;
import com.lemonade.dima.di.MessagesActivityModule;
import com.lemonade.dima.model.Message;
import com.lemonade.dima.model.ResponseMessages;
import com.lemonade.dima.model.User;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesActivity extends AppCompatActivity
        implements MessageInput.InputListener,
        MessagesListAdapter.OnLoadMoreListener,
        MessagesView {
    public static final String START = ":start";
    protected final String senderId = "0";
    protected ImageLoader imageLoader;
    protected MessagesListAdapter<Message> messagesAdapter;
    @Inject
    MessagesPresenter messagesPresenter;
    int selectionCount;
    @BindView(R.id.messagesList)
    MessagesList messagesList;
    @BindView(R.id.input)
    MessageInput input;
    @BindView(R.id.select)
    LinearLayout select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        //AndroidInjection.inject(this);
        MessagesActivityComponent messagesActivityComponent =
                App.applicationComponent.plusMessagesActivityComponent(new MessagesActivityModule(this));
        messagesActivityComponent.inject(this);
        imageLoader = (imageView, url) -> Glide
                .with(MessagesActivity.this)
                .load(url)
                .into(imageView);
        ButterKnife.bind(this);
        messagesAdapter = new MessagesListAdapter<>(senderId, null);
        messagesAdapter.setLoadMoreListener(this);
        messagesList.setAdapter(messagesAdapter);
        input.setInputListener(this);
        send(START);
    }

    @Override
    public void onBackPressed() {
        if (selectionCount == 0) {
            super.onBackPressed();
        } else {
            messagesAdapter.unselectAllItems();
        }
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        if (page == 1)
            messagesPresenter.savedMessages();
    }

    @Override
    public void addMessagesToEnd(List<Message> messages) {
        messagesAdapter.addToEnd(messages, true);
    }

    @Override
    public void addMessages(ResponseMessages responseMessages) {
        setInput(responseMessages);
        for (Message message : responseMessages.getMessages())
            addMessage(message);
    }

    public void addMessage(Message message) {
        messagesAdapter.addToStart(message, true);
    }

    public void setInput(ResponseMessages responseMessages) {
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

    @Override
    public boolean onSubmit(CharSequence input) {
        send(input.toString());
        return true;
    }

    private void send(String text) {
        messagesPresenter.send(new Message(new User(senderId, "Me"), text));
    }
}