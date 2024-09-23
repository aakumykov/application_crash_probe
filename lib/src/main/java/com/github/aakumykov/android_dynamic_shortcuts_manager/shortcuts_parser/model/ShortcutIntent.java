package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model;

import static com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.other.ComparisonUtils.bothNullOrEquals;

import android.content.ComponentName;
import android.content.Intent;

import androidx.annotation.Nullable;

public class ShortcutIntent {

    public static final String ATTR_ACTION = "action";
    public static final String ATTR_TARGET_PACKAGE = "targetPackage";
    public static final String ATTR_TARGET_CLASS = "targetClass";

    public final String action;
    public final String targetPackage;
    public final String targetClass;

    public ShortcutIntent(String action, String targetPackage, String targetClass) {
        this.action = action;
        this.targetPackage = targetPackage;
        this.targetClass = targetClass;
    }

    public Intent toIntent() {
        Intent intent = new Intent();
        intent.setAction(this.action);
        intent.setComponent(new ComponentName(
                this.targetPackage,
                this.targetClass
        ));
        return intent;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (null == obj)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof ShortcutIntent))
            return false;

        final ShortcutIntent si = (ShortcutIntent) obj;

        return this.action.equals(si.action) &&
                this.targetPackage.equals(si.targetPackage) &&
                this.targetClass.equals(si.targetClass);
    }
}
