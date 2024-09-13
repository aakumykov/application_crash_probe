package com.github.aakumykov.kotlin_playground.shortcuts_parser.utils

import android.content.Context
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.RawShortcut
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.toIntent

class RawShortcutResolver(private val resourceResolver: ResourceResolver) {

    fun resolveRawShortcut(context: Context, rawShortcut: RawShortcut): Shortcut {
        return Shortcut(
            shortcutId = rawShortcut.shortcutId,
            enabled = rawShortcut.enabled,
            icon = resourceResolver.getDrawableResourceByName(rawShortcut.icon),
            shortcutShortLabel = resourceResolver.getStringResourceByName(rawShortcut.shortcutShortLabel),
            intent = rawShortcut.shortcutIntent?.toIntent()
        )
    }
}