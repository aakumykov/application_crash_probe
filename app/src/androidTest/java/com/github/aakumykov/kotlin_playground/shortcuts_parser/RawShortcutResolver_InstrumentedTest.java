package com.github.aakumykov.kotlin_playground.shortcuts_parser;

import static com.github.aakumykov.kotlin_playground.common.CommonValues.TARGET_CONTEXT;

import static org.junit.Assert.assertEquals;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.aakumykov.android_dynamic_shortcuts_manager.model.RawShortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.model.Shortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.model.ShortcutIntent;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver;
import com.github.aakumykov.kotlin_playground.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RawShortcutResolver_InstrumentedTest {

    private static final String SHORTCUT_ID = "settings";
    private static final boolean IS_ENABLED = true;
    private static final String ICON_NAME = "settings";
    private static final String SHORT_LABEL_NAME = "shortcut_label_settings";
    private static final String INTENT_ACTION = "net.sourceforge.opencamera.SHORTCUT_SETTINGS";
    private static final String INTENT_TARGET_PACKAGE = "net.sourceforge.opencamera";
    private static final String INTENT_TARGET_CLASS = "net.sourceforge.opencamera.MainActivity";

    private static final RawShortcut SOURCE_RAW_SHORTCUT = new RawShortcut(
            SHORTCUT_ID,
            IS_ENABLED,
            ICON_NAME,
            SHORT_LABEL_NAME,
            new ShortcutIntent(INTENT_ACTION, INTENT_TARGET_PACKAGE, INTENT_TARGET_CLASS)
    );

    private static final Shortcut EXPECTED_SHORTCUT = new Shortcut(
            SHORTCUT_ID,
            IS_ENABLED,
            R.drawable.shortcut_settings,
            R.string.shortcut_label_settings
    );

    @Nullable private RawShortcutResolver rawShortcutResolver;

    @Before
    public void prepareRawShortcutResolver() {
        rawShortcutResolver = RawShortcutResolver.getDefault(TARGET_CONTEXT);
    }

    @Test
    public void when_convert_raw_shortcut_to_shortcut_then_result_matches_expected() {

        if (null == rawShortcutResolver)
            throw new IllegalStateException("rawShortcutResolver field is null");

        final Shortcut shortcut = rawShortcutResolver.resolveRawShortcut(SOURCE_RAW_SHORTCUT);

        assertEquals(EXPECTED_SHORTCUT, shortcut);
    }
}
