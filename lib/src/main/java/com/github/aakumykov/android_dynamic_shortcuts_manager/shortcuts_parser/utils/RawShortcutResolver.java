package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

public class RawShortcutResolver {

    private final ResourceResolver resourceResolver;

    public RawShortcutResolver(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public Shortcut resolveRawShortcut(RawShortcut rawShortcut) {

        Shortcut shortcut = new Shortcut(
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
