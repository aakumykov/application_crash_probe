package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.RawRes;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.RawShortcutResolverJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolverJava;
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ShortcutsXMLRawParserJava;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.IntStream;

import javax.xml.parsers.SAXParserFactory;

public class ShortcutsParserJava {

    /*private final ShortcutsXMLRawParser shortcutsXMLRawParser;
    private final RawShortcutResolverJava rawShortcutResolver;

    public ShortcutsParserJava(ShortcutsXMLRawParser shortcutsXMLRawParser, RawShortcutResolverJava rawShortcutResolver) {
        this.shortcutsXMLRawParser = shortcutsXMLRawParser;
        this.rawShortcutResolver = rawShortcutResolver;
    }

    public static ShortcutsParserJava getDefault(String packageName, Resources resources) {
        return new ShortcutsParserJava(
                new ShortcutsXMLRawParser(SAXParserFactory.newInstance().newSAXParser(), ShortcutsSAXHandler()),
                RawShortcutResolverJava(new ResourceResolverJava(packageName, resources))
        );
    }

    public List<Shortcut> parse(Resources resources, @RawRes int shortcutsXMLRawResource) {

        try (InputStream shortcutsXMLInputStream = resources.openRawResource(shortcutsXMLRawResource)) {
            shortcutsXMLRawParser.parse()
        }
        catch (IOException e) {

        }
    }*/
}
