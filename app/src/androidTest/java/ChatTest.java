import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ChatTest {
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.lemonade.dima";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // Start from the home screen
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testChangeText_sameActivity() {
        assertThat(lastMessage(), is(equalTo(/*"Hello, I am a Server!"*/"What is your name?")));
        device.findObject(By.res(BASIC_SAMPLE_PACKAGE, "messageInput"))
                .setText("Dima");
        device.findObject(By.res(BASIC_SAMPLE_PACKAGE, "messageSendButton"))
                .click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String lastMessage() {
        List<UiObject2> messageTexts = device.wait(
                Until.findObjects(By.res(BASIC_SAMPLE_PACKAGE, "messageText")),
                500);
        int size = messageTexts.size();
        return messageTexts.get(size - 1).getText();
    }
}