package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser;

import android.content.res.Resources;

import androidx.annotation.RawRes;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolver;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class ShortcutsParser {

    private final ShortcutsXMLRawParser shortcutsXMLRawParser;
    private final RawShortcutResolver rawShortcutResolver;

    public ShortcutsParser(ShortcutsXMLRawParser shortcutsXMLRawParser, RawShortcutResolver rawShortcutResolver) {
        this.shortcutsXMLRawParser = shortcutsXMLRawParser;
        this.rawShortcutResolver = rawShortcutResolver;
    }

    public static ShortcutsParser getDefault(String packageName, Resources resources) throws ParserConfigurationException, SAXException {
        return new ShortcutsParser(
                new ShortcutsXMLRawParser(
                        SAXParserFactory.newInstance().newSAXParser(),
                        new ShortcutsSAXHandler()
                ),
                new RawShortcutResolver(new ResourceResolver(packageName, resources))
        );
    }

    public List<Shortcut> parse(Resources resources, @RawRes int shortcutsXMLRawResource) throws SAXException, IOException {

        try (InputStream shortcutsXMLInputStream = resources.openRawResource(shortcutsXMLRawResource)) {
            List<RawShortcut> rawShortcutList = shortcutsXMLRawParser.parse(shortcutsXMLInputStream);
            List<Shortcut> shortcutList = new ArrayList<>();
            for (RawShortcut rawShortcut : rawShortcutList) {
                shortcutList.add(rawShortcutResolver.resolveRawShortcut(rawShortcut));
            }
            return shortcutList;
        }
    }
}
