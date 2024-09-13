package com.github.aakumykov.dynamic_shortcuts_manager.shortcuts_parser.model

import android.content.ComponentName
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

fun ShortcutIntent.toIntent(): Intent {
    return Intent().apply {
        this@apply.action = this@toIntent.action
        this@apply.component = ComponentName(this@toIntent.targetPackage, this@toIntent.targetClass)
    }
}