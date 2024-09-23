package com.github.aakumykov.kotlin_playground

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Rule
import org.junit.Test

class ShortcutsListTests : BasicShortcutsTest() {

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
}