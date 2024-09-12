package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Shortcut(
    var shortcutId: String,
    var enabled: Boolean,
    var icon: String,
    var shortcutShortLabel: String,
)