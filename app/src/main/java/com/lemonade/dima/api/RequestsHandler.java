package com.lemonade.dima.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.lemonade.dima.model.Message;
import com.lemonade.dima.model.ResponseMessages;
import com.lemonade.dima.model.User;
import com.lemonade.dima.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

import static com.lemonade.dima.screen.MessagesActivity.START;

public class RequestsHandler {
    private static int step;

    private static Message inputMessage(@NotNull Request request) {
        BufferedSink bufferedSink = new Buffer();
        try {
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

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @NonNull
    private static Message responseMessage(String text) {
        return new Message(new User("" + 1, "Server"), text);
    }

    @NonNull
    public Response proceed(@NonNull Request request) {
        Message inputMessage = inputMessage(request);
        String inputMessageText = inputMessage.getText();
        if (inputMessageText.equals(START))
            step = 1;
        else if (step == 3 && inputMessageText.equals("NO")) {
            step = 5;
        } else if (step == 4 && inputMessageText.equals("RESTART")) {
            step = 1;
        } else {
            step++;
        }
        List<Message> messages = new ArrayList<>();
        ResponseMessages.ChatInputType chatInputType = null;
        String[] selects = null;
        switch (step) {
            case 1:
                messages.add(responseMessage("Hello, I am a Server!"));
                messages.add(responseMessage("What is your name?"));
                chatInputType = ResponseMessages.ChatInputType.TEXT;
                break;
            case 2:
                messages.add(responseMessage("Nice to meet you " + inputMessageText + " :)"));
                messages.add(responseMessage("What is your phone number?"));
                chatInputType = ResponseMessages.ChatInputType.NUMBER;
                break;
            case 3:
                messages.add(responseMessage("Do you agree to our terms of service?"));
                chatInputType = ResponseMessages.ChatInputType.SELECT;
                selects = new String[]{"NO", "YES"};
                break;
            case 4:
                messages.add(responseMessage("Thanks!"));
                messages.add(responseMessage("This is the last step!"));
                messages.add(responseMessage("What do you want to do now?"));
                chatInputType = ResponseMessages.ChatInputType.SELECT;
                selects = new String[]{"RESTART", "EXIT"};
                break;
            case 5:
                messages.add(responseMessage("Bye Bye!"));
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
            return OkHttpResponse.error(request, 500, e.getMessage());
        }
    }
}