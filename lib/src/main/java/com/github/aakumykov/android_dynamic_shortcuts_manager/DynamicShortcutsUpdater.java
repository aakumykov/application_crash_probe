package com.github.aakumykov.android_dynamic_shortcuts_manager;

import android.content.Context;

import androidx.annotation.RawRes;

import com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager.DynamicShortcutManager;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.ShortcutsParser;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

import org.xml.sax.SAXException;

import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class DynamicShortcutsUpdater {

    public static DynamicShortcutsUpdater getDefault(Context context, @RawRes int shortcutsXMLRawResource) throws ParserConfigurationException, SAXException {
        return new DynamicShortcutsUpdater(
                ShortcutsParser.getDefault(context.getPackageName(), context.getResources()),
                new DynamicShortcutManager(context),
                shortcutsXMLRawResource
        );
    }

    private final ShortcutsParser shortcutsParser;
    private final DynamicShortcutManager dynamicShortcutManager;
    private @RawRes int shortcutsXMLRawResource;

    public DynamicShortcutsUpdater(
            ShortcutsParser shortcutsParser,
            DynamicShortcutManager dynamicShortcutManager,
            @RawRes int shortcutsXMLRawResource
    ) {
        this.shortcutsParser = shortcutsParser;
        this.dynamicShortcutManager = dynamicShortcutManager;
    }

    public void updateShortcuts(List<Shortcut> shortcutListToAdd, List<Shortcut> shortcutListToRemove) throws Exception {
        dynamicShortcutManager.removeDynamicShortcuts(shortcutListToRemove);
        dynamicShortcutManager.createDynamicShortcuts(shortcutListToAdd);
    }
}
