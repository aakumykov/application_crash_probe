package com.github.aakumykov.android_dynamic_shortcuts_manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutIntent;

import org.junit.Test;

public class ShortcutIntentUnitTest {

    private static final String SOME_ACTION = "some_action";
    private static final String OTHER_ACTION = "other_action";

    private static final String SOME_PACKAGE_NAME = "package1";
    private static final String OTHER_PACKAGE_NAME = "package2";

    private static final String SOME_CLASS_NAME = "class1";
    private static final String OTHER_CLASS_NAME = "class2";

    private static final ShortcutIntent si1 = new ShortcutIntent(SOME_ACTION, SOME_PACKAGE_NAME, SOME_CLASS_NAME);
    private static final ShortcutIntent si2 = new ShortcutIntent(SOME_ACTION, SOME_PACKAGE_NAME, SOME_CLASS_NAME);
    private static final ShortcutIntent other_si = new ShortcutIntent(OTHER_ACTION, OTHER_PACKAGE_NAME, OTHER_CLASS_NAME);


    @Test
    public void when_compare_two_same_shortcut_intents_in_direct_order_then_are_equals() {
        assertTrue(si1.equals(si2));
    }

    @Test
    public void when_compare_two_same_shortcut_intents_in_reverse_order_then_are_equals() {
        assertTrue(si2.equals(si1));
    }


    @Test
    public void when_compare_different_shortcut_intents_in_direct_order_then_are_not_equals() {
        assertFalse(si1.equals(other_si));
    }

    @Test
    public void when_compare_different_shortcut_intents_in_reverse_order_then_are_not_equals() {
        assertFalse(other_si.equals(si1));
    }


    @Test
    public void when_compare_shortcut_intent_with_null_then_false() {
        assertFalse(si1.equals(null));
    }
}
