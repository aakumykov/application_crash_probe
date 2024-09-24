package com.github.aakumykov.kotlin_playground.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.aakumykov.android_dynamic_shortcuts_manager.model.Shortcut;
import com.github.aakumykov.kotlin_playground.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Shortcut_Equals_UnitTest {

    private static final String SOME_SHORTCUT_ID = "settings";
    private static final boolean SOME_SHORTCUT_ENABLED = true;
    private static final int SOME_SHORTCUT_ICON = R.drawable.shortcut_settings;
    private static final int SOME_SHORTCUT_SHORT_LABEL = R.string.shortcut_label_settings;
    private static final Intent INTENT1 = openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_SETTINGS");
    private static final Intent INTENT2 = openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_SETTINGS");

    public static Intent openCameraIntentWithAction(String action) {
        Intent intent = new Intent(action);
        intent.setClassName("net.sourceforge.opencamera","net.sourceforge.opencamera.MainActivity");
        return intent;
    }

    private static final Shortcut SHORTCUT_1 = new Shortcut(
        SOME_SHORTCUT_ID,
        SOME_SHORTCUT_ENABLED,
        SOME_SHORTCUT_ICON,
        SOME_SHORTCUT_SHORT_LABEL,
        INTENT1
    );

    private static final Shortcut SHORTCUT_2 = new Shortcut(
        SOME_SHORTCUT_ID,
        SOME_SHORTCUT_ENABLED,
        SOME_SHORTCUT_ICON,
        SOME_SHORTCUT_SHORT_LABEL,
        INTENT2
    );

    private static final Shortcut OTHER_SHORTCUT = new Shortcut(
        "gallery",
        true,
        R.drawable.shortcut_gallery,
        R.string.shortcut_label_gallery,
        openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_GALLERY")
    );


    @Test
    public void when_compare_two_same_shortcuts_in_direct_order_then_are_equals() {
        assertTrue(SHORTCUT_1.equals(SHORTCUT_2));
    }

    @Test
    public void when_compare_two_same_shortcuts_in_reverse_order_then_are_equals() {
        assertTrue(SHORTCUT_2.equals(SHORTCUT_1));
    }


    @Test
    public void when_compare_different_shortcuts_in_direct_order_then_are_not_equals() {
        assertFalse(SHORTCUT_1.equals(OTHER_SHORTCUT));
    }

    @Test
    public void when_compare_different_shortcuts_in_reverse_order_then_are_not_equals() {
        assertFalse(OTHER_SHORTCUT.equals(SHORTCUT_1));
    }


    @Test
    public void when_compare_shortcut_with_null_then_false() {
        assertFalse(SHORTCUT_1.equals(null));
    }
}
