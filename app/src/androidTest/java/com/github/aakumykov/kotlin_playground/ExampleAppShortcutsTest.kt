package com.github.aakumykov.kotlin_playground

import androidx.annotation.IdRes
import androidx.annotation.StringRes
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
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 25)
class ExampleAppShortcutsTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private val defaultShortcutList = listOf(
        string(R.string.shortcut_label_settings),
        string(R.string.shortcut_label_gallery),
        string(R.string.shortcut_label_record_video),
        string(R.string.shortcut_label_selfie),
    )


    @Test
    fun calendar_shouldDisplayNewEventShortcut() {
        openAllApps(device)
        openAppShortcuts(device, R.string.calendar)
        verifyAppShortcuts(listOf(string(R.string.action_new_event)))
    }


    @Test
    fun onAppInstallDefaultShortcutsMustBeCreated() {
        openAllApps(device)
        openSelfShortcuts(device)
        /*verifyAppShortcuts(listOf(
            string(R.string.shortcut_label_settings)
        ))*/
    }


    @Test
    fun shouldDisplayDefaultShortcutsAfterCreatingThem() {
        openAllApps(device)
        openSelfApp(device)
        clickButton(R.id.createDefaultShortcuts)


        openAllApps(device)
        openSelfShortcuts(device)
        verifyAppShortcuts(defaultShortcutList)
    }


    @Test
    fun shouldNoDisplayAppShortcutsAfterDeleteAll() {
        openAllApps(device)
        openSelfApp(device)
        clickButton(R.id.removeAllShortcuts)

        openAllApps(device)
        openSelfShortcuts(device)
        verifyNoAppShortcuts(defaultShortcutList)
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


    /*@After
    fun tearDown() {
        pressHome(device)
    }*/



    private fun pressHome(device: UiDevice) {
        device.pressHome()
    }


    private fun openAllApps(device: UiDevice) {

        device.pressHome()

        if (android.os.Build.VERSION.SDK_INT == 25) {
            openAppByName(device, "Apps list")
        } else {
            device.findObject(By.res("com.google.android.apps.nexuslauncher:id/workspace"))
                .fling(Direction.DOWN)
        }
    }


    private fun openSelfApp(device: UiDevice) {
        openAppByName(device, string(R.string.app_name))
    }


    private fun openSelfAppFromAllApps(device: UiDevice) {
        openAllApps(device)
        openSelfApp(device)
    }


    private fun openSelfShortcuts(device: UiDevice) {
        openAppShortcuts(device, R.string.app_name)
    }


    private fun clickButton(@IdRes buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }


    private fun openAppByName(device: UiDevice, appName: String) {
        device.findObject(By.desc(appName)).click()
    }


    private fun openAppShortcuts(device: UiDevice, @StringRes nameRes: Int) {
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

val targetContext get() = InstrumentationRegistry.getInstrumentation().targetContext

fun string(@StringRes strRes: Int): String {
    return targetContext.getString(strRes)
}