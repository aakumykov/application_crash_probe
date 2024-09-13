package com.github.aakumykov.android_dynamic_shortcuts_manager.dynamic_shortcut_manager

import android.content.Context
import androidx.core.content.pm.ShortcutManagerCompat
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.toShortcutInfo

class DynamicShortcutManager(private val context: Context) {

    /**
     * @param List of [Shortcut] objects. Must be <= 4.
     * @return Number of created shortcuts.
     */
    fun createDynamicShortcuts(list: List<Shortcut>): Result<Int> {
        return list.map { shortcutList ->
            shortcutList.toShortcutInfo(context)
        }
            .let { shortcutInfoList ->
                try {
                    ShortcutManagerCompat.addDynamicShortcuts(context, shortcutInfoList)
                    Result.success(shortcutInfoList.size)
                }
                catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }

    fun removeDynamicShortcuts(list: List<Shortcut>) {
        list.map { it.shortcutId }.also { idList ->
            ShortcutManagerCompat.removeDynamicShortcuts(context, idList)
        }
    }
}