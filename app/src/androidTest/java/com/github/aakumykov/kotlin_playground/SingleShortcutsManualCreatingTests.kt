package com.github.aakumykov.kotlin_playground

import androidx.annotation.StringRes
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

class SingleShortcutsManualCreatingTests : ListClickingTest() {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @After
    fun tearDown() { showDesktop(device) }


    // Настройки
    @Test
    fun when_check_settings_and_press_update_shortcuts_then_creates_only_that_shortcut() {
        checkSingleSelectedShortcutManuallyCreated(R.string.shortcut_label_settings)
    }

    // Камера
    @Test
    fun when_check_camera_and_press_update_shortcuts_then_creates_only_that_shortcut() {
        checkSingleSelectedShortcutManuallyCreated(R.string.shortcut_label_camera)
    }

    // Видео
    @Test
    fun when_check_video_and_press_update_shortcuts_then_creates_only_that_shortcut() {
        checkSingleSelectedShortcutManuallyCreated(R.string.shortcut_label_settings)
    }

    // Селфи
    @Test
    fun when_check_selfie_and_press_update_shortcuts_then_creates_only_that_shortcut() {
        checkSingleSelectedShortcutManuallyCreated(R.string.shortcut_label_camera)
    }

    // Галарея
    @Test
    fun when_check_gallery_and_press_update_shortcuts_then_creates_only_that_shortcut() {
        checkSingleSelectedShortcutManuallyCreated(R.string.shortcut_label_camera)
    }



    private fun checkSingleSelectedShortcutManuallyCreated(@StringRes shortcutShortLabelId: Int) {

        clickListItemWithShortcutShortLabel(shortcutShortLabelId)
        clickUpdateShortcutsButton()

        openAllApps(device)
        openSelfShortcuts(device)
        verifyAppShortcuts(listOf(
            string(shortcutShortLabelId)
        ))
    }
}