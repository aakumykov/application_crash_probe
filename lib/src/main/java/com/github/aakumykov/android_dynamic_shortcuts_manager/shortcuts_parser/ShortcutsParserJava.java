package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser;

import android.content.res.Resources;

import androidx.annotation.RawRes;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.RawShortcutJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.ShortcutJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolverJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolverJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsSAXHandler;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParserJava;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class ShortcutsParserJava {

    private final ShortcutsXMLRawParserJava shortcutsXMLRawParser;
    private final RawShortcutResolverJava rawShortcutResolver;

    public ShortcutsParserJava(ShortcutsXMLRawParserJava shortcutsXMLRawParser, RawShortcutResolverJava rawShortcutResolver) {
        this.shortcutsXMLRawParser = shortcutsXMLRawParser;
        this.rawShortcutResolver = rawShortcutResolver;
    }

    public static ShortcutsParserJava getDefault(String packageName, Resources resources) throws ParserConfigurationException, SAXException {
        return new ShortcutsParserJava(
                new ShortcutsXMLRawParserJava(
                        SAXParserFactory.newInstance().newSAXParser(),
                        new ShortcutsSAXHandler()
                ),
                new RawShortcutResolverJava(new ResourceResolverJava(packageName, resources))
        );
    }

    public List<ShortcutJava> parse(Resources resources, @RawRes int shortcutsXMLRawResource) throws SAXException, IOException {

        try (InputStream shortcutsXMLInputStream = resources.openRawResource(shortcutsXMLRawResource)) {
            List<RawShortcutJava> rawShortcutJavaList = shortcutsXMLRawParser.parse(shortcutsXMLInputStream);
            List<ShortcutJava> shortcutList = new ArrayList<>();
            for (RawShortcutJava rawShortcut : rawShortcutJavaList) {
                shortcutList.add(rawShortcutResolver.resolveRawShortcut(rawShortcut));
            }
            return shortcutList;
        }
    }
}
