package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;

public class ShortcutsXMLRawParser {

    private final SAXParser saxParser;
    private final ShortcutsSAXHandler shortcutsSAXHandler;

    public ShortcutsXMLRawParser(SAXParser saxParser, ShortcutsSAXHandler shortcutsSAXHandler) {
        this.saxParser = saxParser;
        this.shortcutsSAXHandler = shortcutsSAXHandler;
    }

    public List<RawShortcut> parse(InputStream shortcutsXMLRawResourceInputStream) throws java.io.IOException, org.xml.sax.SAXException {
        saxParser.parse(shortcutsXMLRawResourceInputStream, shortcutsSAXHandler);
        return shortcutsSAXHandler.getShortcuts();
    }
}
