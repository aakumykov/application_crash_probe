package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class RawShortcutResolver(private val resourceResolver: ResourceResolver) {

    fun resolveRawShortcut(context: Context, rawShortcut: RawShortcut): Shortcut {
        return Shortcut(
            shortcutId = rawShortcut.shortcutId,
            enabled = rawShortcut.enabled,
            icon = resourceResolver.getDrawableResourceByName(rawShortcut.icon),
            shortcutShortLabel = resourceResolver.getStringResourceByName(rawShortcut.shortcutShortLabel),
            intent = rawShortcut.shortcutIntent?.toIntent(context)
        )
    }
}


class ResourceResolver(private val packageName: String, private val resources: Resources) {

    @StringRes
    fun getStringResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, STRING, packageName)
    }

    @DrawableRes
    fun getDrawableResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, STRING, packageName)
    }

    companion object {
        const val STRING = "string"
        const val DRAWABLE = "drawable"
    }
}
