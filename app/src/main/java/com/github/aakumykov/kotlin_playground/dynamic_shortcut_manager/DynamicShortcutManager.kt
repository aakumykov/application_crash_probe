package com.github.aakumykov.kotlin_playground.dynamic_shortcut_manager

import android.content.Context
import androidx.core.content.pm.ShortcutManagerCompat
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.toShortcutInfo

class DynamicShortcutManager(private val context: Context) {

    fun recreateShortcuts(list: List<Shortcut>) {
        removeAllShortcuts()
        createShortcutsFromList(list)
    }

    fun createShortcutsFromList(list: List<Shortcut>) {
        list.map {
            it.toShortcutInfo(context)
        }.also {
            ShortcutManagerCompat.addDynamicShortcuts(context, it)
        }
    }

    fun removeAllShortcuts() {
        ShortcutManagerCompat.removeAllDynamicShortcuts(context)
    }
}