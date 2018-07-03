package com.k4dima.chat.core.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.k4dima.chat.R;
import com.k4dima.chat.core.model.Message;
import com.k4dima.chat.core.model.ResponseMessages;
import com.k4dima.chat.core.model.User;
import com.k4dima.chat.core.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

import static com.k4dima.chat.features.messages.MessagesActivity.START;

@Singleton
public class RequestsHandler {
    private static int step;
    private final Context context;

    @Inject
    RequestsHandler(Context context) {
        this.context = context;
    }

    private static Message inputMessage(@NotNull Request request) {
        BufferedSink bufferedSink = new Buffer();
        try {
            //noinspection ConstantConditions
            request.body().writeTo(bufferedSink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream inputStream = bufferedSink.buffer().inputStream();
        String string = convertStreamToString(inputStream);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(string, Message.class);
    }

    private static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @NonNull
    private static Message responseMessage(String text) {
        return new Message(new User("" + 1, "Server"), text);
    }

    @NonNull
    private Message message(int id) {
        return responseMessage(string(id));
    }

    @NonNull
    private String string(int id) {
        return context.getString(id);
    }

    @NonNull
    Response proceed(@NonNull Request request) {
        String inputMessageText = inputMessage(request).getText();
        String no = "NO";
        String restart = "RESTART";
        if (inputMessageText.equals(START))
            step = 1;
        else if (step == 3 && inputMessageText.equals(no))
            step = 5;
        else if (step == 4 && inputMessageText.equals(restart))
            step = 1;
        else
            step++;
        List<Message> messages = new ArrayList<>();
        ResponseMessages.ChatInputType chatInputType = null;
        String[] selects = null;
        switch (step) {
            case 1:
                messages.add(message(R.string.hello));
                messages.add(message(R.string.name));
                chatInputType = ResponseMessages.ChatInputType.TEXT;
                break;
            case 2:
                messages.add(responseMessage(context.getString(R.string.meet, inputMessageText)));
                messages.add(message(R.string.number));
                chatInputType = ResponseMessages.ChatInputType.NUMBER;
                break;
            case 3:
                messages.add(message(R.string.agree));
                chatInputType = ResponseMessages.ChatInputType.SELECT;
                selects = new String[]{no, string(R.string.yes)};
                break;
            case 4:
                messages.add(message(R.string.thanks));
                messages.add(message(R.string.last));
                messages.add(message(R.string.todo));
                chatInputType = ResponseMessages.ChatInputType.SELECT;
                selects = new String[]{restart, string(R.string.exit)};
                break;
            case 5:
                messages.add(message(R.string.bye));
                chatInputType = ResponseMessages.ChatInputType.TEXT;
                step = 0;
                break;
        }
        ResponseMessages responseMessages = new ResponseMessages(messages, chatInputType, selects);
        JsonObject jsonObject = AppUtils.toJson(responseMessages);
        String jsonObjectAsString = jsonObject.toString();
        try {
            try (InputStream stream = new ByteArrayInputStream(jsonObjectAsString.getBytes())) {
                return OkHttpResponse.success(request, stream);
            }
        } catch (IOException e) {
            return OkHttpResponse.error(request, e.getMessage());
        }
    }
}