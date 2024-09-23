package com.github.aakumykov.kotlin_playground

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import org.hamcrest.Description
import org.hamcrest.Matcher

abstract class ListClickingTest : BasicShortcutsTest() {


    fun clickListItemWithShortcutShortLabel(shortLabelStringRes: Int) {
        onData(withShortcutShortLabel(shortLabelStringRes))
            .perform(click())
    }


    fun clickUpdateShortcutsButton() {
        clickButton(R.id.updateShortcutsButton)
    }


    private fun withShortcutShortLabel(value: Int): Matcher<Any?> {
        return object: BoundedMatcher<Any?, Shortcut>(Shortcut::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has value $value")
            }

            override fun matchesSafely(item: Shortcut?): Boolean {
                return item?.shortcutShortLabel == value
            }
        }
    }
}
