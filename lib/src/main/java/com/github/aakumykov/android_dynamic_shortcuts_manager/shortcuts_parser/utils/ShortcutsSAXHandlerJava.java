package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutIntentJava;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ShortcutsSAXHandlerJava extends DefaultHandler {

    private static final String TAG = ShortcutsSAXHandlerJava.class.getSimpleName();

    private static final String SHORTCUT_TAG_NAME = "shortcut";
    private static final String INTENT_TAG_NAME = "intent";

    private final List<RawShortcutJava> rawShortcutList = new ArrayList<>();
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
                        new RawShortcutJava(
                                attributes.getValue(RawShortcutJava.ATTR_SHORTCUT_ID),
                                Boolean.valueOf(attributes.getValue(RawShortcutJava.ATTR_ENABLED)),
                                attributes.getValue(RawShortcutJava.ATTR_ICON),
                                attributes.getValue(RawShortcutJava.ATTR_SHORTCUT_SHORT_LABEL)
                        ));
                break;

            case INTENT_TAG_NAME:
                @Nullable RawShortcutJava lastRawShortcut = rawShortcutList.get(rawShortcutList.size()-1);
                if (null != lastRawShortcut) {
                    rawShortcutList.get(rawShortcutList.size() - 1).shortcutIntent = new ShortcutIntentJava(
                            attributes.getValue(ShortcutIntentJava.ATTR_ACTION),
                            attributes.getValue(ShortcutIntentJava.ATTR_TARGET_PACKAGE),
                            attributes.getValue(ShortcutIntentJava.ATTR_TARGET_CLASS)
                    );
                } else {
                    Log.e(TAG, "Try to set 'shortcutIntent' property to RawShortcutJava.");
                }
                break;

            default:
                Log.w(TAG, "Unknown xml tag '"+qName+"'");
        }
    }

    public List<RawShortcutJava> getShortcuts() {
        return rawShortcutList;
    }


}
