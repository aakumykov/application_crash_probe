package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat

data class Shortcut(
    val shortcutId: String,
    val enabled: Boolean,
    @DrawableRes val icon: Int,
    @StringRes val shortcutShortLabel: Int,
    @StringRes val shortcutLongLabel: Int? = null,
    @StringRes val shortcutDisabledMessage: Int? = null,
    val intent: Intent?,
)

fun Shortcut.toShortcutInfo(context: Context): ShortcutInfoCompat {
    return ShortcutInfoCompat.Builder(context, shortcutId)
        .apply {
            setShortLabel(context.getString(shortcutShortLabel))
            shortcutLongLabel?.also { setLongLabel(context.getString(it)) }
            setIcon(IconCompat.createWithResource(context, icon))
            intent?.also { setIntent(it) }
            shortcutDisabledMessage?.also { setDisabledMessage(context.getString(it)) }
        }.build()
}