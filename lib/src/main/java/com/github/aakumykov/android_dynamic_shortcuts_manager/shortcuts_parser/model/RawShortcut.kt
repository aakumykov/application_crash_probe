package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model

data class RawShortcut(
    var shortcutId: String,
    var enabled: Boolean,
    var icon: String,
    var shortcutShortLabel: String,
    var shortcutIntent: ShortcutIntent? = null,
) {
    companion object {
        const val ATTR_SHORTCUT_ID = "shortcutId"
        const val ATTR_ENABLED = "enabled"
        const val ATTR_ICON = "icon"
        const val ATTR_SHORTCUT_SHORT_LABEL = "shortcutShortLabel"
    }
}