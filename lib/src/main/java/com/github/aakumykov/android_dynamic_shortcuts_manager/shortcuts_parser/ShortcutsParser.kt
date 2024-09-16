package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolverJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser
import javax.xml.parsers.SAXParserFactory

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

    companion object {
        @JvmStatic
        fun getDefault(packageName: String, resources: Resources): ShortcutsParser {
            return ShortcutsParser(
                ShortcutsXMLRawParser(SAXParserFactory.newInstance().newSAXParser(), ShortcutsSAXHandler()),
                RawShortcutResolver(ResourceResolverJava(packageName, resources))
            )
        }
    }
}