package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model;

import androidx.annotation.Nullable;

public class RawShortcut {

    public static final String ATTR_SHORTCUT_ID = "shortcutId";
    public static final String ATTR_ENABLED = "enabled";
    public static final String ATTR_ICON = "icon";
    public static final String ATTR_SHORTCUT_SHORT_LABEL = "shortcutShortLabel";

    public final String shortcutId;
    public final Boolean enabled;
    public final String icon;
    public final String shortcutShortLabel;
    @Nullable public String shortcutLongLabel;
    @Nullable public String shortcutDisabledMessage;
    @Nullable public ShortcutIntent shortcutIntent;

    public RawShortcut(String shortcutId, Boolean enabled, String icon, String shortcutShortLabel) {
        this.shortcutId = shortcutId;
        this.enabled = enabled;
        this.icon = icon;
        this.shortcutShortLabel = shortcutShortLabel;
    }

    public void setShortcutIntent(@Nullable ShortcutIntent shortcutIntent) {
        this.shortcutIntent = shortcutIntent;
    }

    public void setShortcutLongLabel(@Nullable String shortcutLongLabel) {
        this.shortcutLongLabel = shortcutLongLabel;
    }

    public void setShortcutDisabledMessage(@Nullable String shortcutDisabledMessage) {
        this.shortcutDisabledMessage = shortcutDisabledMessage;
    }
}