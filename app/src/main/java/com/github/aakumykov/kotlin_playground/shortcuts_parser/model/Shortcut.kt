package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Shortcut(
    val shortcutId: String,
    val enabled: Boolean,
    @DrawableRes val icon: Int,
    @StringRes val shortcutShortLabel: Int,
    val intent: Intent?,
)
