package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils

import android.content.Context
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.toIntent

class RawShortcutResolver(private val resourceResolver: ResourceResolver) {

    fun resolveRawShortcut(context: Context, rawShortcut: RawShortcutJava): Shortcut {
        return Shortcut(
            shortcutId = rawShortcut.shortcutId,
            enabled = rawShortcut.enabled,
            icon = resourceResolver.getDrawableResourceByName(rawShortcut.icon),
            shortcutShortLabel = resourceResolver.getStringResourceByName(rawShortcut.shortcutShortLabel),
            intent = rawShortcut.shortcutIntent?.toIntent()
        )
    }
}