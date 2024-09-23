package com.github.aakumykov.kotlin_playground

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import junit.framework.TestCase.assertEquals
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShortcutsListTests {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @After
    fun tearDown() {
        showDesktop(device)
    }



    @Test
    fun when_check_settings_and_press_update_shortcuts_then_creates_only_settings_shortcut() {

        clickListItemWithShortcutShortLabel(R.string.shortcut_label_settings)
        clickButton(R.id.updateShortcutsButton)

        openAllApps(device)
        openSelfShortcuts(device)
        verifyAppShortcuts(listOf(
            string(R.string.shortcut_label_settings)
        ))
    }


    private fun clickButton(buttonId: Int) {
        onView(withId(buttonId)).perform(click())
    }


    private fun verifyAppShortcuts(shortcuts: List<String>) {
        shortcuts.forEach { appShortcut ->
            assertEquals(appShortcut, device.findObject(By.text(appShortcut)).text)
        }
    }


    private fun clickListItemWithShortcutShortLabel(shortLabelStringRes: Int) {
        onData(withShortcutShortLabel(shortLabelStringRes))
            .perform(click())
    }


    private fun withShortcutShortLabel(value: Int): Matcher<Any?> {
        return object: BoundedMatcher<Any?,Shortcut>(Shortcut::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has value $value")
            }

            override fun matchesSafely(item: Shortcut?): Boolean {
                return item?.shortcutShortLabel == value
            }
        }
    }


    private fun openSelfAppFromAllApps(device: UiDevice) {
        openAllApps(device)
        openSelfApp(device)
    }


    private fun openAllApps(device: UiDevice) {
        showDesktop(device)
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


    private fun openAppByName(device: UiDevice, appName: String) {
        device.findObject(By.desc(appName)).click()
    }


    private fun openSelfShortcuts(device: UiDevice) {
        openAppShortcuts(device, R.string.app_name)
    }


    private fun openAppShortcuts(device: UiDevice, @StringRes nameRes: Int) {
        device.findObject(By.desc(string(nameRes))).longClick()
    }


    private fun showDesktop(device: UiDevice) {
        device.pressHome()
    }
}