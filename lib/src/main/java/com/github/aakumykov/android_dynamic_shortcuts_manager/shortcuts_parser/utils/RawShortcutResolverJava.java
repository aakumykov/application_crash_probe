package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutJava;

public class RawShortcutResolverJava {

    private final ResourceResolverJava resourceResolver;

    public RawShortcutResolverJava(ResourceResolverJava resourceResolverJava) {
        this.resourceResolver = resourceResolverJava;
    }

    public ShortcutJava resolveRawShortcut(RawShortcutJava rawShortcut) {

        ShortcutJava shortcut = new ShortcutJava(
                rawShortcut.shortcutId,
                resourceResolver.getDrawableResourceByName(rawShortcut.icon),
                resourceResolver.getStringResourceByName(rawShortcut.shortcutShortLabel)
        );

        // Long label
        if (null != rawShortcut.shortcutLongLabel)
            shortcut.shortcutLongLabel = resourceResolver.getStringResourceByName(rawShortcut.shortcutLongLabel);

        // Disabled message
        if (null != rawShortcut.shortcutDisabledMessage)
            shortcut.shortcutDisabledMessage = resourceResolver.getStringResourceByName(rawShortcut.shortcutDisabledMessage);

        // Intent
        if (null != rawShortcut.shortcutIntent)
            shortcut.shortcutIntent = rawShortcut.shortcutIntent.toIntent();

        return shortcut;
    }
}
