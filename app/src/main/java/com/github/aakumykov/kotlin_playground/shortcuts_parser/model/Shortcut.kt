package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.alpha
import androidx.core.graphics.drawable.IconCompat
import com.github.aakumykov.kotlin_playground.R

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
            setShortLabel("Website")
            shortcutLongLabel?.also { setLongLabel(context.getString(it)) }
            setIcon(IconCompat.createWithResource(context, icon))
            intent?.also { setIntent(it) }
            shortcutDisabledMessage?.also { setDisabledMessage(context.getString(it)) }
        }.build()
}