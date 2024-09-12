package com.github.aakumykov.kotlin_playground.shortcuts_parser

import android.provider.LiveFolders.INTENT
import android.provider.UserDictionary.Words.SHORTCUT
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class ShortcutsHandler : DefaultHandler() {

    private lateinit var shortcuts: MutableList<Shortcut>
    private val elementValue: StringBuilder = StringBuilder()

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        elementValue.appendRange(ch, start, start + length)
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        shortcuts = mutableListOf()
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, lName: String, qName: String, attributes: Attributes) {
        when (qName) {
            SHORTCUT -> {
                shortcuts.add(Shortcut(
                    shortcutId = attributes.getValue(ATTR_SHORTCUT_ID),
                    icon = attributes.getValue(ATTR_ICON),
                    enabled = attributes.getValue(ATTR_ENABLED).toBoolean(),
                    shortcutShortLabel = attributes.getValue(ATTR_SHORTCUT_SHORT_LABEL)
                ))
            }
            INTENT -> {}
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        when (qName) {
            SHORTCUTS -> {}
            SHORTCUT -> {}
            INTENT -> {}
        }
    }

    fun getShortcuts(): List<Shortcut> = shortcuts

    companion object {
        const val SHORTCUTS = "shortcuts"
        const val SHORTCUT = "shortcut"
        const val INTENT = "intent"

        const val ATTR_SHORTCUT_ID = "shortcutId"
        const val ATTR_ENABLED = "enabled"
        const val ATTR_ICON = "icon"
        const val ATTR_SHORTCUT_SHORT_LABEL = "shortcutShortLabel"

    }
}