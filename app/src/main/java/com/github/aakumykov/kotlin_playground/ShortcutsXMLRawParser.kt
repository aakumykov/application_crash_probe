package com.github.aakumykov.kotlin_playground

import com.github.aakumykov.kotlin_playground.shortcuts_parser.ShortcutsHandler
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.RawShortcut
import java.io.InputStream
import javax.xml.parsers.SAXParser

class ShortcutsXMLRawParser(
    private val saxParser: SAXParser,
    private val shortcutsHandler: ShortcutsHandler,
) {
    fun parse(shortcutsXMLInputStream: InputStream): Result<List<RawShortcut>> {
        return try {
            saxParser.parse(shortcutsXMLInputStream, shortcutsHandler)
            Result.success(shortcutsHandler.getShortcuts())
        }
        catch (e: Exception) {
            Result.failure(e)
        }
    }
}
