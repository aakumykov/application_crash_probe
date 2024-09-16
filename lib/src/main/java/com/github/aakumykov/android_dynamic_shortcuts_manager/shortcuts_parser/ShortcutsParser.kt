package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolverJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolverJava
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParserJava
import javax.xml.parsers.SAXParserFactory

class ShortcutsParser(
    private val shortcutsXMLRawParser: ShortcutsXMLRawParserJava,
    private val rawShortcutResolver: RawShortcutResolverJava,
) {
    fun parse(context: Context, @RawRes shortcutsXMLRawResource: Int): Result<List<Shortcut>> {

        val shortcutsXMLInputStream = context.resources.openRawResource(shortcutsXMLRawResource)

        try {
            val rawShortcutList: List<RawShortcutJava> = shortcutsXMLRawParser.parse(shortcutsXMLInputStream)
            val shortcutList: List<Shortcut> = rawShortcutList.map { rawShortcut -> rawShortcutResolver.resolveRawShortcut(rawShortcut) }
            return Result.success(shortcutList);
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

    companion object {
        @JvmStatic
        fun getDefault(packageName: String, resources: Resources): ShortcutsParser {
            return ShortcutsParser(
                ShortcutsXMLRawParserJava(SAXParserFactory.newInstance().newSAXParser(), ShortcutsSAXHandler()),
                RawShortcutResolverJava(ResourceResolverJava(packageName, resources))
            )
        }
    }
}