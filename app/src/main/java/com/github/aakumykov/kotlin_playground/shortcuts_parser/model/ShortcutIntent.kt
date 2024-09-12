package com.github.aakumykov.kotlin_playground.shortcuts_parser.model

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