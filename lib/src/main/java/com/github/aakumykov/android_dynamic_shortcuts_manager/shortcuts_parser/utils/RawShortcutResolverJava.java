package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

public class RawShortcutResolverJava {

    private final ResourceResolverJava resourceResolver;

    public RawShortcutResolverJava(ResourceResolverJava resourceResolverJava) {
        this.resourceResolver = resourceResolverJava;
    }

    public Shortcut resolveRawShortcut(RawShortcutJava rawShortcut) {

        Shortcut shortcut = new Shortcut(
                rawShortcut.shortcutId,
                resourceResolver.getDrawableResourceByName(rawShortcut.icon),
                resourceResolver.getStringResourceByName(rawShortcut.shortcutShortLabel),
                null,
                null,
                null
        );

        if (null != rawShortcut.shortcutIntent)
            shortcut.setIntent(rawShortcut.shortcutIntent.toIntent());

        return shortcut;
    }
}
