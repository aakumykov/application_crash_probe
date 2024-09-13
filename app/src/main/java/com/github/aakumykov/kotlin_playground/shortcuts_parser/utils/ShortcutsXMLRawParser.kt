package com.github.aakumykov.kotlin_playground.shortcuts_parser.utils

import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.RawShortcut
import java.io.InputStream
import javax.xml.parsers.SAXParser

class ShortcutsXMLRawParser(
    private val saxParser: SAXParser,
    private val shortcutsSAXHandler: ShortcutsSAXHandler,
) {
    fun parse(shortcutsXMLInputStream: InputStream): Result<List<RawShortcut>> {
        return try {
            saxParser.parse(shortcutsXMLInputStream, shortcutsSAXHandler)
            Result.success(shortcutsSAXHandler.getShortcuts())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}
