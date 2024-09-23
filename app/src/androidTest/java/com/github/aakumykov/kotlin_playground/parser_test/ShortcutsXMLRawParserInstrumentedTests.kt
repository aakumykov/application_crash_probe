package com.github.aakumykov.kotlin_playground.parser_test

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.aakumykov.android_dynamic_shortcuts_manager.R
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutIntent
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser
import com.github.aakumykov.kotlin_playground.utils.resources
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShortcutsXMLRawParserInstrumentedTests {

    private val expectedRawShortcutList = listOf(
        RawShortcut(
            "settings",
            false,
            "@drawable/shortcut_settings",
            "@string/shortcut_label_settings",
            ShortcutIntent(
                "net.sourceforge.opencamera.SHORTCUT_SETTINGS",
                "net.sourceforge.opencamera",
                "net.sourceforge.opencamera.MainActivity"
            )
        ),

        RawShortcut(
            "gallery",
            false,
            "@drawable/shortcut_gallery",
            "@string/shortcut_label_gallery",
            ShortcutIntent(
                "net.sourceforge.opencamera.SHORTCUT_GALLERY",
                "net.sourceforge.opencamera",
                "net.sourceforge.opencamera.MainActivity"
            )
        ),

        RawShortcut(
            "video",
            false,
            "@drawable/shortcut_ic_videocam_white_48dp",
            "@string/shortcut_label_record_video",
            ShortcutIntent(
                "net.sourceforge.opencamera.SHORTCUT_VIDEO",
                "net.sourceforge.opencamera",
                "net.sourceforge.opencamera.MainActivity"
            )
        ),

        RawShortcut(
            "selfie",
            false,
            "@drawable/shortcut_ic_face_white_48dp",
            "@string/shortcut_label_selfie",
            ShortcutIntent(
                "net.sourceforge.opencamera.SHORTCUT_SELFIE",
                "net.sourceforge.opencamera",
                "net.sourceforge.opencamera.MainActivity"
            )
        ),

        RawShortcut(
            "camera",
            false,
            "@drawable/shortcut_ic_photo_camera_white_48dp",
            "@string/shortcut_label_camera",
            ShortcutIntent(
                "net.sourceforge.opencamera.SHORTCUT_CAMERA",
                "net.sourceforge.opencamera",
                "net.sourceforge.opencamera.MainActivity"
            )
        ),
    )

    private val rawParser = ShortcutsXMLRawParser.getDefault()

    @Test
    fun when_parse_raw_shortcuts_xml_when_count_equals_expected() {
        val listOfRaw = parse()
        assertEquals(expectedRawShortcutList.size, listOfRaw.size)
    }

    @Test
    fun when_pause_raw_shortcuts_xml_when_corresponding_objects_equals() {
        parse().forEach { rawShortcut: RawShortcut ->
            val expectedRawShortcut = expectedRawShortcutList.firstOrNull { it.shortcutId == rawShortcut.shortcutId }
            assertEquals(expectedRawShortcut, rawShortcut)
        }
    }

    private fun parse(): List<RawShortcut>
        = rawParser.parse(resources.openRawResource(com.github.aakumykov.kotlin_playground.R.raw.shortcuts))


}