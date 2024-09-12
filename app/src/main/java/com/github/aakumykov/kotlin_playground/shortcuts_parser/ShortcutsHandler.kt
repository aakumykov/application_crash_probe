package com.github.aakumykov.kotlin_playground.shortcuts_parser

import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut.Companion.ATTR_ENABLED
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut.Companion.ATTR_ICON
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut.Companion.ATTR_SHORTCUT_ID
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.Shortcut.Companion.ATTR_SHORTCUT_SHORT_LABEL
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.ShortcutIntent
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.ShortcutIntent.Companion.ATTR_ACTION
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.ShortcutIntent.Companion.ATTR_TARGET_CLASS
import com.github.aakumykov.kotlin_playground.shortcuts_parser.model.ShortcutIntent.Companion.ATTR_TARGET_PACKAGE
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
            INTENT -> {
                shortcuts.last().shortcutIntent = ShortcutIntent(
                    action = attributes.getValue(ATTR_ACTION),
                    targetPackage = attributes.getValue(ATTR_TARGET_PACKAGE),
                    targetClass = attributes.getValue(ATTR_TARGET_CLASS)
                )
            }
        }
    }

    fun getShortcuts(): List<Shortcut> = shortcuts

    companion object {
        const val SHORTCUTS = "shortcuts"
        const val SHORTCUT = "shortcut"
        const val INTENT = "intent"
    }
}