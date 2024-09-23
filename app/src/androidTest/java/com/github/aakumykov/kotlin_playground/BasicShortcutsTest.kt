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
import org.junit.Assert.assertNull
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 25)
open class BasicShortcutsTest {


    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())


    fun pressHome(device: UiDevice) {
        device.pressHome()
    }


    fun openAllApps(device: UiDevice) {

        pressHome(device)

        if (android.os.Build.VERSION.SDK_INT == 25) {
            openAppByName(device, "Apps list")
        } else {
            device.findObject(By.res("com.google.android.apps.nexuslauncher:id/workspace"))
                .fling(Direction.DOWN)
        }
    }


    fun openSelfApp(device: UiDevice) {
        openAppByName(device, string(R.string.app_name))
    }


    fun openSelfAppFromAllApps(device: UiDevice) {
        openAllApps(device)
        openSelfApp(device)
    }


    fun openSelfShortcuts(device: UiDevice) {
        openAppShortcuts(device, R.string.app_name)
    }


    fun clickButton(@IdRes buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }


    fun openAppByName(device: UiDevice, appName: String) {
        device.findObject(By.desc(appName)).click()
    }


    fun openAppShortcuts(device: UiDevice, @StringRes appNameRes: Int) {
        device.findObject(By.desc(string(appNameRes))).longClick()
    }


    fun verifyAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertEquals(appShortcut, device.findObject(By.text(appShortcut)).text)
        }
    }


    fun verifyNoAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertNull(device.findObject(By.text(appShortcut)))
        }
    }


    fun showDesktop(device: UiDevice) {
        device.pressHome()
    }
}
