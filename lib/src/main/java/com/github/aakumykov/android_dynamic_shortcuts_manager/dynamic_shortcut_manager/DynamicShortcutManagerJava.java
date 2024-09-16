package com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager;

import android.content.Context;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutJava;

import java.util.ArrayList;
import java.util.List;

public class DynamicShortcutManagerJava {

    // TODO: ShortcutManagerCompat.getMaxShortcutCountPerActivity()

    private final Context context;

    public DynamicShortcutManagerJava(Context context) {
        this.context = context;
    }

    public void createDynamicShortcuts(List<ShortcutJava> list) throws IllegalArgumentException {
        List<ShortcutInfoCompat> shortcutInfoCompatList = new ArrayList<>();
        for (ShortcutJava shortcut : list) {
            shortcutInfoCompatList.add(shortcut.toShortcutInfo(context));
        }
        ShortcutManagerCompat.addDynamicShortcuts(context, shortcutInfoCompatList);
    }

    public void removeDynamicShortcuts(List<ShortcutJava> list) {
        List<String> idList = new ArrayList<>();
        for (ShortcutJava shortcut : list) {
            idList.add(shortcut.shortcutId);
        }
        ShortcutManagerCompat.removeDynamicShortcuts(context, idList);
    }
}
