package com.lemonade.dima.screen;

import com.lemonade.dima.model.Message;
import com.lemonade.dima.repository.LemonadeRepository;

import javax.inject.Inject;

import static com.lemonade.dima.screen.MessagesActivity.START;

public class MessagesPresenter {
    private final MessagesView messagesView;
    private LemonadeRepository repository;

    @Inject
    MessagesPresenter(MessagesView messagesView, LemonadeRepository repository) {
        this.messagesView = messagesView;
        this.repository = repository;
    }

    public void send(Message message) {
        if (!message.getText().equals(START))
            messagesView.addMessage(message);
        repository
                .message(message)
                .subscribe(messagesView::addMessages,
                        Throwable::printStackTrace);
    }

    public void savedMessages() {
        repository
                .saved()
                .subscribe(messages -> {
                    for (int i = 0; i < 2; i++)
                        messages.remove(messages.size() - 1);
                    if (messages.size() != 0)
                        messagesView.addMessagesToEnd(messages);
                });
    }
}