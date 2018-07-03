package com.k4dima.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.k4dima.chat.BuildConfig.APPLICATION_ID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class WalkthroughTest {
    private UiDevice device;
    private Context context = InstrumentationRegistry.getTargetContext();

    @Before
    public void startMainActivityFromHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(APPLICATION_ID);
        //noinspection ConstantConditions
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(APPLICATION_ID).depth(0)), 5000);
    }

    @Test
    public void testWalkthrough() {
        checkPrevious(R.string.hello);
        checkLast(R.string.name);
        String name = "Dima";
        text(name);
        send();
        sleep();
        checkPrevious(context.getString(R.string.meet, name));
        checkLast(R.string.number);
        text("0633289001");
        send();
        sleep();
        click(R.string.yes);
        sleep();
        check(3, R.string.thanks);
        checkPrevious(R.string.last);
        checkLast(R.string.todo);
        click(R.string.exit);
        sleep();
        checkLast(R.string.bye);
        sleep();
    }

    private void click(int id) {
        find(By.text(string(id))).click();
    }

    private UiObject2 find(BySelector selector) {
        return device.findObject(selector);
    }

    @NonNull
    private String string(int id) {
        return context.getString(id);
    }

    private void checkPrevious(int id) {
        checkPrevious(string(id));
    }

    private void checkPrevious(String match) {
        check(message(2), match);
    }

    private void checkLast(int id) {
        check(1, id);
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void send() {
        object("messageSendButton").click();
    }

    private void text(String text) {
        object("messageInput").setText(text);
    }

    private UiObject2 object(String resourceId) {
        return find(selector(resourceId));
    }

    private void check(String actual, String match) {
        assertThat(actual, is(equalTo(match)));
    }

    private void check(int index, int id) {
        check(message(index), string(id));
    }

    private BySelector selector(String resourceId) {
        return By.res(APPLICATION_ID, resourceId);
    }

    private String message(int position) {
        List<UiObject2> messageTexts = device.wait(Until.findObjects(selector("messageText")), 500);
        int size = messageTexts.size();
        return messageTexts.get(size - position).getText();
    }
}