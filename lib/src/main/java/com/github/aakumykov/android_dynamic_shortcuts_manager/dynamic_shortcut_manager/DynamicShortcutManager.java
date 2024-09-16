package com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager;

import android.content.Context;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

import java.util.ArrayList;
import java.util.List;

public class DynamicShortcutManager {

    public static final int DEFAULT_MAX_SHORTCUTS_COUNT = 4;

    private final Context context;
    private final int maxSupportedShortcutsCount;


    public DynamicShortcutManager(Context context) {
        this.context = context;
        maxSupportedShortcutsCount = DEFAULT_MAX_SHORTCUTS_COUNT;
    }


    public DynamicShortcutManager(Context context, int maxSupportedShortcutsCount) {
        this.context = context;
        this.maxSupportedShortcutsCount = maxSupportedShortcutsCount;
    }


    public void createDynamicShortcuts(List<Shortcut> list) throws IllegalArgumentException {
        List<ShortcutInfoCompat> shortcutInfoCompatList = new ArrayList<>();
        for (Shortcut shortcut : list) {
            shortcutInfoCompatList.add(shortcut.toShortcutInfo(context));
        }
        ShortcutManagerCompat.addDynamicShortcuts(context, shortcutInfoCompatList);
    }


    public void removeDynamicShortcuts(List<Shortcut> list) {
        List<String> idList = new ArrayList<>();
        for (Shortcut shortcut : list) {
            idList.add(shortcut.shortcutId);
        }
        ShortcutManagerCompat.removeDynamicShortcuts(context, idList);
    }


    public int getMaxSupportedShortcutsCount() {
        return ShortcutManagerCompat.getMaxShortcutCountPerActivity(context);
    }

    public boolean isDynamicShortcutsSupported() {
        return getMaxSupportedShortcutsCount() > 0;
    }
}
