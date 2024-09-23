package com.github.aakumykov.kotlin_playground

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.CoreMatchers.anything
import org.junit.Test


class ExampleAppShortcutsTest : BasicShortcutsTest() {

    private val defaultShortcutList = listOf(
        string(R.string.shortcut_label_settings),
        string(R.string.shortcut_label_gallery),
        string(R.string.shortcut_label_record_video),
        string(R.string.shortcut_label_selfie),
    )


    /*@Test
    fun calendar_should_display_NewEvent_shortcut() {
        openAllApps(device)
        openAppShortcuts(device, R.string.calendar)
        verifyAppShortcuts(listOf(string(R.string.action_new_event)))
    }*/


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
    fun should_no_display_app_shortcuts_after_delete_all_shortcuts_button_pressed() {
        openAllApps(device)
        openSelfApp(device)
        clickButton(R.id.removeAllShortcuts)

        openAllApps(device)
        openSelfShortcuts(device)
        verifyNoAppShortcuts(defaultShortcutList)
    }


// Check Adapterview is displayed or not and perform click on second item. onData(allOf(withProductID(1)))
//        onData(anything())
//            .inAdapterView(allOf(isAssignableFrom(AdapterView.class), isDisplayed()))
//            .perform(click());


    @Test
    fun should_click_on_1st_list_item() {
        openSelfAppFromAllApps(device)

        onData(anything())
            .inAdapterView(withId(R.id.listView))
            .atPosition(0)
            .perform(click())

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


    /*@After
    fun tearDown() {
        pressHome(device)
    }*/




}