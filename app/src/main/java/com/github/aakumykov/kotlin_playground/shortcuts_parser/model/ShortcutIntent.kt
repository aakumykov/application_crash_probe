package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

import android.content.ComponentName
import android.content.Context
import android.content.Intent

data class ShortcutIntent(
    val action: String,
    val targetPackage: String,
    val targetClass: String,
) {
    companion object {
        const val ATTR_ACTION = "action"
        const val ATTR_TARGET_PACKAGE = "targetPackage"
        const val ATTR_TARGET_CLASS = "targetClass"
    }
}

fun ShortcutIntent.toIntent(context: Context): Intent {
    return Intent().apply {
        this.action = action
        this.component = ComponentName(targetPackage, targetClass)
    }
}