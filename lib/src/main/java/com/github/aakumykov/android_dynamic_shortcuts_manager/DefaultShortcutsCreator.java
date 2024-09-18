package com.github.aakumykov.android_dynamic_shortcuts_manager;

import android.content.Context;

import androidx.annotation.RawRes;

import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class DefaultShortcutsCreator {

    private final ShortcutsParser shortcutsParser;
    private final DynamicShortcutManager dynamicShortcutManager;


    public DefaultShortcutsCreator(
            ShortcutsParser shortcutsParser,
            DynamicShortcutManager dynamicShortcutManager
    ) {
        this.shortcutsParser = shortcutsParser;
        this.dynamicShortcutManager = dynamicShortcutManager;
    }


    public static DefaultShortcutsCreator getDefault(Context context) throws ParserConfigurationException, SAXException {
        return new DefaultShortcutsCreator(
                ShortcutsParser.getDefault(context.getPackageName(), context.getResources()),
                new DynamicShortcutManager(context)
        );
    }


    public void initShortcuts(List<Shortcut> addedShortcutKeys, List<Shortcut> removedShortcutKeys) throws Exception {
        dynamicShortcutManager.removeDynamicShortcuts(removedShortcutKeys);
        dynamicShortcutManager.createDynamicShortcuts(addedShortcutKeys);
    }


    public void initShortcuts(@RawRes int shortcutsXMLRawResource) throws Exception {

        List<Shortcut> enabledList = new ArrayList<>();
        List<Shortcut> disabledList = new ArrayList<>();

        for (Shortcut shortcut : shortcutsParser.parse(shortcutsXMLRawResource)) {
            if (shortcut.enabled) enabledList.add(shortcut);
            else disabledList.add(shortcut);
        }

        dynamicShortcutManager.removeDynamicShortcuts(disabledList);
        dynamicShortcutManager.createDynamicShortcuts(enabledList);
    }
}
