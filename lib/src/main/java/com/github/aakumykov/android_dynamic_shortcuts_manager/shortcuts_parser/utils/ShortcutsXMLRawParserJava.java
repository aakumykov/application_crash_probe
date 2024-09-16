package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;

public class ShortcutsXMLRawParserJava {

    private final SAXParser saxParser;
    private final ShortcutsSAXHandler shortcutsSAXHandler;

    public ShortcutsXMLRawParserJava(SAXParser saxParser, ShortcutsSAXHandler shortcutsSAXHandler) {
        this.saxParser = saxParser;
        this.shortcutsSAXHandler = shortcutsSAXHandler;
    }

    public List<RawShortcutJava> parse(InputStream shortcutsXMLRawResourceInputStream) throws java.io.IOException, org.xml.sax.SAXException {
        saxParser.parse(shortcutsXMLRawResourceInputStream, shortcutsSAXHandler);
        return shortcutsSAXHandler.getShortcuts();
    }
}
