package com.k4dima.chat

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.k4dima.chat.BuildConfig.APPLICATION_ID
import com.k4dima.chat.R.string.*
import com.k4dima.chat.app.Chat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit.SECONDS

@RunWith(AndroidJUnit4::class)
class WalkthroughTest {
    private val timeout = SECONDS.toMillis(3)
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Chat>()

    @Test
    fun testWalkthrough() {
        // hello
        context.packageManager.getLaunchIntentForPackage(APPLICATION_ID)
                .run { context.startActivity(this) }
        checkLastMessage(hello)
        // name
        checkLastMessage(name)
        "Dima".run {
            text(this)
            checkLastMessages(context.getString(meet, this))
        }
        // number
        checkLastMessage(number)
        text("+380633289001")
        // terms
        checkLastMessage(agree)
        click(yes)
        checkLastMessage(thanks)
        // Do
        checkLastMessage(last, todo)
        click(exit)
        checkLastMessage(bye)
    }

    private fun checkLastMessage(vararg expectedMessages: Int) =
            expectedMessages.map { string(it) }
                    .toTypedArray()
                    .run { checkLastMessages(*this) }

    private fun checkLastMessages(vararg expectedMessages: String) =
            expectedMessages.map {
                device.findObject(UiSelector().text(it)).waitForExists(timeout)
            }
                    .forEach { assertTrue(it) }

    private fun click(id: Int) =
            device.findObject(UiSelector().text(string(id)))
                    .run {
                        waitForExists(timeout)
                        click()
                    }

    private fun string(id: Int) = context.getString(id)

    private fun text(text: String) {
        uiObject("messageInput").text = text
        uiObject("messageSendButton").click()
    }

    private fun uiObject(resourceId: String) = device.findObject(By.res(APPLICATION_ID, resourceId))
}