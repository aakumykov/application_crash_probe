package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat

data class Shortcut(
    val shortcutId: String,
    @DrawableRes val icon: Int,
    @StringRes val shortcutShortLabel: Int,
    @StringRes var shortcutLongLabel: Int? = null,
    @StringRes var shortcutDisabledMessage: Int? = null,
    var intent: Intent?,
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