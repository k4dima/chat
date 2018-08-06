package com.k4dima.chat

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.BySelector
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.Until
import com.k4dima.chat.BuildConfig.APPLICATION_ID
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WalkthroughTest {
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = InstrumentationRegistry.getTargetContext()

    @Before
    fun startMainActivity() {
        val intent = context.packageManager.getLaunchIntentForPackage(APPLICATION_ID)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(APPLICATION_ID).depth(0)), 5000)
    }

    @Test
    fun testWalkthrough() {
        checkPrevious(R.string.hello)
        checkLast(R.string.name)
        val name = "Dima"
        text(name)
        send()
        sleep()
        checkPrevious(context.getString(R.string.meet, name))
        checkLast(R.string.number)
        text("0633289001")
        send()
        sleep()
        click(R.string.yes)
        sleep()
        check(3, R.string.thanks)
        checkPrevious(R.string.last)
        checkLast(R.string.todo)
        click(R.string.exit)
        sleep()
        checkLast(R.string.bye)
        sleep()
    }

    private fun click(id: Int) = find(By.text(string(id))).click()

    private fun find(selector: BySelector) = device.findObject(selector)

    private fun string(id: Int) = context.getString(id)

    private fun checkPrevious(id: Int) = checkPrevious(string(id))

    private fun checkPrevious(match: String) = assertEquals(message(2), match)

    private fun checkLast(id: Int) = check(1, id)

    private fun sleep() =
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

    private fun send() = uiObject("messageSendButton").click()

    private fun text(text: String) {
        uiObject("messageInput").text = text
    }

    private fun uiObject(resourceId: String) = find(selector(resourceId))

    private fun check(index: Int, id: Int) = assertEquals(message(index), string(id))

    private fun selector(resourceId: String) = By.res(APPLICATION_ID, resourceId)

    private fun message(position: Int): String {
        val messageTexts = device.wait(Until.findObjects(selector("messageText")), 500)
        val size = messageTexts.size
        return messageTexts[size - position].text
    }
}