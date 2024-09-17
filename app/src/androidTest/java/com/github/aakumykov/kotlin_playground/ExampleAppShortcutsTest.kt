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
    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun shouldDisplayStaticShortcutsForCalendarApp() {
        openAllApps(device)
        openAppShortcuts(device, getString(R.string.app_name))
        verifyAppShortcuts(device, listOf("New event", "New reminder"))
    }

    @After
    fun tearDown() {
        device.pressHome()
    }

    private fun getString(@StringRes strRes: Int): String {
        return context.getString(strRes)
    }
}

fun openAllApps(device: UiDevice) {
    device.pressHome()

    if (android.os.Build.VERSION.SDK_INT == 25) {
        device.findObject(By.desc("Apps list"))
            .click()
    } else {
        device.findObject(By.res("com.google.android.apps.nexuslauncher:id/workspace"))
            .fling(Direction.DOWN)
    }
}

fun openAppShortcuts(device: UiDevice, name: String
) {
    device.findObject(By.desc(name)).longClick()
}


fun verifyAppShortcuts(device: UiDevice, shortcuts: List<String>) {
    shortcuts.forEach { appShortcut ->
        assertEquals(appShortcut, device.findObject(By.text(appShortcut)).text)
    }
}

