package com.github.aakumykov.kotlin_playground

import androidx.annotation.StringRes
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 25)
class ExampleAppShortcutsTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = InstrumentationRegistry.getInstrumentation().targetContext


    @Test
    fun openSelfApp() {
        openAllApps()
        openAppByName(getString(R.string.app_name))
    }


    @Test
    fun shouldDisplayDefaultShortcutsForSelfApp() {

        openAllApps()

        openAppShortcuts(getString(R.string.app_name))

        verifyAppShortcuts(listOf(
            getString(R.string.action_settings),
            getString(R.string.gallery),
            getString(R.string.record_video),
            getString(R.string.selfie),
        ))
    }


    @Test
    fun shouldDisplay() {
        openAllApps()
        openAppShortcuts(getString(R.string.calendar))
        verifyAppShortcuts(listOf(getString(R.string.action_new_event)))
    }


    @Test
    fun openSystemSettingsFromHomeScreen() {
        pressHome();
        openAppByName(getString(R.string.settings))
    }


    @Test
    fun openSystemSettingsFromAllApps() {
        openAllApps()
        openAppByName(getString(R.string.settings))
    }


    @After
    fun tearDown() {
        device.pressHome()
    }


    private fun getString(@StringRes strRes: Int): String {
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


    private fun openAppByName(appName: String) {
        device.findObject(By.desc(appName)).click()
    }


    private fun openAppShortcuts(name: String) {
        device.findObject(By.desc(name)).longClick()
    }


    private fun verifyAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertEquals(appShortcut, device.findObject(By.text(appShortcut)).text)
        }
    }
}

