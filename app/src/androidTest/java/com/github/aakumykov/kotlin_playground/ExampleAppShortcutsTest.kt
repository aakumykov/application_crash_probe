package com.github.aakumykov.kotlin_playground

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.anything
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 25)
class ExampleAppShortcutsTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val defaultShortcutList = listOf(
        string(R.string.shortcut_label_settings),
        string(R.string.shortcut_label_gallery),
        string(R.string.shortcut_label_record_video),
        string(R.string.shortcut_label_selfie),
    )


    @Test
    fun calendar_shouldDisplayNewEventShortcut() {
        openAllApps()
        openAppShortcuts(R.string.calendar)
        verifyAppShortcuts(listOf(string(R.string.action_new_event)))
    }


    @Test
    fun onAppInstallDefaultShortcutsMustBeCreated() {
        openAllApps()
        openSelfShortcuts()
        verifyAppShortcuts(listOf(
            string(R.string.shortcut_label_settings)
        ))
    }


    @Test
    fun shouldDisplayDefaultShortcutsAfterCreatingThem() {
        openAllApps()
        openSelfApp()
        clickButton(R.id.createDefaultShortcuts)


        openAllApps()
        openSelfShortcuts()
        verifyAppShortcuts(defaultShortcutList)
    }


    @Test
    fun shouldNoDisplayAppShortcutsAfterDeleteAll() {
        openAllApps()
        openSelfApp()
        clickButton(R.id.removeAllShortcuts)

        openAllApps()
        openSelfShortcuts()
        verifyNoAppShortcuts(defaultShortcutList)
    }


// Check Adapterview is displayed or not and perform click on second item. onData(allOf(withProductID(1)))
//        onData(anything())
//            .inAdapterView(allOf(isAssignableFrom(AdapterView.class), isDisplayed()))
//            .perform(click());


    @Test
    fun shoud_click_on_list_item() {
        openSelfAppFromAllApps()

        onData(anything())
            .inAdapterView(withId(R.id.listView))
            .atPosition(0).perform(click())

        clickButton(R.id.updateShortcutsButton)

    }


    /*@Test
    fun openSystemSettingsFromHomeScreen() {
        pressHome()
        openAppByName(string(R.string.app_name_settings))
    }*/


    /*@Test
    fun openSystemSettingsFromAllApps() {
        openAllApps()
        openAppByName(string(R.string.app_name_settings))
    }*/


    @After
    fun tearDown() {
        pressHome()
    }



    private fun string(@StringRes strRes: Int): String {
        return context.getString(strRes)
    }


    private fun pressHome() {
        device.pressHome()
    }


    private fun openAllApps() {

        device.pressHome()

        if (android.os.Build.VERSION.SDK_INT == 25) {
            openAppByName("Apps list")
        } else {
            device.findObject(By.res("com.google.android.apps.nexuslauncher:id/workspace"))
                .fling(Direction.DOWN)
        }
    }


    private fun openSelfApp() {
        openAppByName(string(R.string.app_name))
    }


    private fun openSelfAppFromAllApps() {
        openAllApps()
        openSelfApp()
    }

    private fun openSelfShortcuts() {
        openAppShortcuts(R.string.app_name)
    }


    private fun clickButton(@IdRes buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }


    private fun openAppByName(appName: String) {
        device.findObject(By.desc(appName)).click()
    }


    private fun openAppShortcuts(@StringRes nameRes: Int) {
        device.findObject(By.desc(string(nameRes))).longClick()
    }


    private fun verifyAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertEquals(appShortcut, device.findObject(By.text(appShortcut)).text)
        }
    }

    private fun verifyNoAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertNull(device.findObject(By.text(appShortcut)))
        }
    }
}

