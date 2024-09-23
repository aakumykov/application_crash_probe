package com.github.aakumykov.kotlin_playground

import org.junit.After
import org.junit.Test

class MassiveAppShortcutsTest : BasicShortcutsTest() {

    @After
    fun tearDown() { showDesktop(device) }


    private val defaultShortcutList = listOf(
        string(R.string.shortcut_label_settings),
        string(R.string.shortcut_label_gallery),
        string(R.string.shortcut_label_record_video),
        string(R.string.shortcut_label_selfie),
    )


    @Test
    fun default_shortcuts_must_be_created_on_app_install() {
        openAllApps(device)
        openSelfShortcuts(device)
        verifyAppShortcuts(listOf(
            string(R.string.shortcut_label_settings)
        ))
    }


    @Test
    fun should_display_default_shortcuts_after_creating_them() {
        openAllApps(device)
        openSelfApp(device)
        clickButton(R.id.createDefaultShortcuts)

        openAllApps(device)
        openSelfShortcuts(device)
        verifyAppShortcuts(defaultShortcutList)
    }


    @Test
    fun should_no_display_app_shortcuts_after_removing_all() {
        openAllApps(device)
        openSelfApp(device)
        clickButton(R.id.removeAllShortcuts)

        openAllApps(device)
        openSelfShortcuts(device)
        verifyNoAppShortcuts(defaultShortcutList)
    }
}