package com.github.aakumykov.android_dynamic_shortcuts_manager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut;

import org.junit.Test;

public class RawShortcutUnitTest {

    //
    // Тест неполный! Test are not complete!
    //

    private static final String SOME_SHORTCUT_ID = "some_shortcut_id";
    private static final String OTHER_SHORTCUT_ID = "other_shortcut_id";

    private static final boolean ENABLED = true;
    private static final boolean DISABLED = false;

    private static final String SOME_ICON = "some_icon";
    private static final String OTHER_ICON = "other_icon";

    private static final String SOME_SHORT_LABEL = "some_short_label";
    private static final String OTHER_SHORT_LABEL = "other_short_label";

//    private static final String SOME_ = "some_";
//    private static final String OTHER_ = "other_";


    private static final RawShortcut rs1 = new RawShortcut(
            SOME_SHORTCUT_ID, ENABLED, SOME_ICON, SOME_SHORT_LABEL
    );

    private static final RawShortcut rs2 = new RawShortcut(
            SOME_SHORTCUT_ID, ENABLED, SOME_ICON, SOME_SHORT_LABEL
    );

    private static final RawShortcut otherRawShortcut = new RawShortcut(
            OTHER_SHORTCUT_ID, ENABLED, OTHER_ICON, OTHER_SHORT_LABEL
    );


    @Test
    public void when_compare_same_shortcuts_in_direct_order_then_they_are_equals() {
        assertTrue(rs1.equals(rs2));
    }

    @Test
    public void when_compare_same_shortcuts_in_reverse_order_then_they_are_equals() {
        assertTrue(rs2.equals(rs1));
    }


    @Test
    public void when_compare_different_shortcuts_in_direct_order_then_they_are_not_equals() {
        assertFalse(rs1.equals(otherRawShortcut));
    }

    @Test
    public void when_compare_different_shortcuts_in_reverse_order_then_they_are_not_equals() {
        assertFalse(otherRawShortcut.equals(rs1));
    }


    @Test
    public void when_compare_raw_shortcut_with_null_then_not_equals() {
        assertFalse(rs1.equals(null));
    }
}
