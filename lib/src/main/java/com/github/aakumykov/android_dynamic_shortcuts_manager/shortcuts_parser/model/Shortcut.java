package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;

public class Shortcut {

    public final String shortcutId;
    public final boolean enabled;
    @DrawableRes public final int icon;
    @StringRes public final int shortcutShortLabel;

    @StringRes @Nullable public Integer shortcutLongLabel;
    @StringRes @Nullable public Integer shortcutDisabledMessage;
    @Nullable public Intent shortcutIntent;

    public Shortcut(String shortcutId, boolean enabled, int icon, int shortcutShortLabel) {
        this.shortcutId = shortcutId;
        this.enabled = enabled;
        this.icon = icon;
        this.shortcutShortLabel = shortcutShortLabel;
    }

    public ShortcutInfoCompat toShortcutInfo(Context context) {
        ShortcutInfoCompat.Builder builder = new ShortcutInfoCompat.Builder(context, shortcutId);

        builder.setShortLabel(context.getString(shortcutShortLabel));
        builder.setIcon(IconCompat.createWithResource(context, icon));

        // Long label
        if (null != shortcutLongLabel)
            builder.setLongLabel(context.getString(shortcutLongLabel));

        // Disabled message
        if (null != shortcutDisabledMessage)
            builder.setDisabledMessage(context.getString(shortcutDisabledMessage));

        // Shortcut intent
        if (null != shortcutIntent)
            builder.setIntent(shortcutIntent);

        return builder.build();
    }
}
