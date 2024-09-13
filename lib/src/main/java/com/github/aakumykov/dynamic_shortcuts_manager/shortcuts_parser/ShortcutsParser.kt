package com.github.aakumykov.dynamic_shortcuts_manager.shortcuts_parser

import android.content.Context
import androidx.annotation.RawRes
import com.github.aakumykov.dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser

class ShortcutsParser(
    private val shortcutsXMLRawParser: ShortcutsXMLRawParser,
    private val rawShortcutResolver: RawShortcutResolver,
) {
    fun parse(context: Context, @RawRes shortcutsXMLRawResource: Int): Result<List<Shortcut>> {

        val shortcutsXMLInputStream = context.resources.openRawResource(shortcutsXMLRawResource)

        return shortcutsXMLRawParser.parse(shortcutsXMLInputStream)
            .mapCatching { rawList ->
                rawList.map {  rawShortcut ->
                    rawShortcutResolver.resolveRawShortcut(context,rawShortcut)
                }
            }
    }
}