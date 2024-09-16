package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutIntent;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ShortcutsSAXHandler extends DefaultHandler {

    private static final String TAG = ShortcutsSAXHandler.class.getSimpleName();

    private static final String SHORTCUT_TAG_NAME = "shortcut";
    private static final String INTENT_TAG_NAME = "intent";

    private final List<RawShortcut> rawShortcutList = new ArrayList<>();
    private final StringBuilder elementValue = new StringBuilder();


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue.append(ch, start, start + length);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case SHORTCUT_TAG_NAME:
                rawShortcutList.add(
                        new RawShortcut(
                                attributes.getValue(RawShortcut.ATTR_SHORTCUT_ID),
                                Boolean.valueOf(attributes.getValue(RawShortcut.ATTR_ENABLED)),
                                attributes.getValue(RawShortcut.ATTR_ICON),
                                attributes.getValue(RawShortcut.ATTR_SHORTCUT_SHORT_LABEL)
                        ));
                break;

            case INTENT_TAG_NAME:
                @Nullable RawShortcut lastRawShortcut = rawShortcutList.get(rawShortcutList.size()-1);
                if (null != lastRawShortcut) {
                    rawShortcutList.get(rawShortcutList.size() - 1).shortcutIntent = new ShortcutIntent(
                            attributes.getValue(ShortcutIntent.ATTR_ACTION),
                            attributes.getValue(ShortcutIntent.ATTR_TARGET_PACKAGE),
                            attributes.getValue(ShortcutIntent.ATTR_TARGET_CLASS)
                    );
                } else {
                    Log.e(TAG, "Try to set 'shortcutIntent' property to RawShortcut.");
                }
                break;

            default:
                Log.w(TAG, "Unknown xml tag '"+qName+"'");
        }
    }

    public List<RawShortcut> getShortcuts() {
        return rawShortcutList;
    }


}
