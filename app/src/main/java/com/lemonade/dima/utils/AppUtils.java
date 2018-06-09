package com.lemonade.dima.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class AppUtils {
    public static void showToast(Context context, @StringRes int text, boolean isLong) {
        showToast(context, context.getString(text), isLong);
    }

    private static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public static JsonObject toJson(Object messages) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return (JsonObject) gson.toJsonTree(messages);
    }
}