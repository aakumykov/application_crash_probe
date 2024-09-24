package com.github.aakumykov.kotlin_playground.ui_tests

import com.github.aakumykov.kotlin_playground.R
import com.github.aakumykov.kotlin_playground.test_utils.string
import org.junit.Test


class ExampleAppShortcutsTest : BasicShortcutsTest() {


    @Test
    fun calendar_should_display_NewEvent_shortcut() {
        openAllApps(device)
        openAppShortcuts(device, R.string.calendar)
        verifyAppShortcuts(listOf(string(R.string.action_new_event)))
    }


// Check Adapterview is displayed or not and perform click on second item. onData(allOf(withProductID(1)))
//        onData(anything())
//            .inAdapterView(allOf(isAssignableFrom(AdapterView.class), isDisplayed()))
//            .perform(click());


    /*@Test
    fun should_click_on_1st_list_item() {
        openSelfAppFromAllApps(device)

        onData(anything())
            .inAdapterView(withId(R.id.listView))
            .atPosition(0)
            .perform(click())

        clickButton(R.id.updateShortcutsButton)

    }*/
}