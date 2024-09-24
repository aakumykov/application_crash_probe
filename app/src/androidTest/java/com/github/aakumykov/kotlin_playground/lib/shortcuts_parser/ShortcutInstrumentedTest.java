package com.github.aakumykov.kotlin_playground.lib.shortcuts_parser;

import android.content.Intent;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;
import com.github.aakumykov.kotlin_playground.R;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ShortcutInstrumentedTest {

    private static final List<Shortcut> EXPECTED_SHORTCUT_LIST = Arrays.asList(
        new Shortcut(
            "settings",
            true,
            R.drawable.shortcut_settings,
            R.string.shortcut_label_settings,
            openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_SETTINGS")
        ),
        new Shortcut(
                "gallery",
                true,
                R.drawable.shortcut_gallery,
                R.string.shortcut_label_gallery,
                openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_GALLERY")
        ),
        new Shortcut(
                "video",
                true,
                R.drawable.shortcut_ic_videocam_white_48dp,
                R.string.shortcut_label_record_video,
                openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_VIDEO")
        ),
        new Shortcut(
                "selfie",
                true,
                R.drawable.shortcut_ic_face_white_48dp,
                R.string.shortcut_label_selfie,
                openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_SELFIE")
        ),
        new Shortcut(
                "camera",
                false,
                R.drawable.shortcut_ic_photo_camera_white_48dp,
                R.string.shortcut_label_camera,
                openCameraIntentWithAction("net.sourceforge.opencamera.SHORTCUT_CAMERA")
        )
    );


    private static Intent openCameraIntentWithAction(String action) {
        Intent intent = new Intent(action);
        intent.setClassName("net.sourceforge.opencamera","net.sourceforge.opencamera.MainActivity");
        return intent;
    }


    @Test
    public void when_parse_raw_shortcuts_xml_then_count_equals_expected() {

    }
}
