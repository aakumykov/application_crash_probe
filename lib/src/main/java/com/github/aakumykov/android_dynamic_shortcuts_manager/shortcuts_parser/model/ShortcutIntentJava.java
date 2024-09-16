package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model;

import android.content.ComponentName;
import android.content.Intent;

public class ShortcutIntentJava {

    public static final String ATTR_ACTION = "action";
    public static final String ATTR_TARGET_PACKAGE = "targetPackage";
    public static final String ATTR_TARGET_CLASS = "targetClass";

    public final String action;
    public final String targetPackage;
    public final String targetClass;

    public ShortcutIntentJava(String action, String targetPackage, String targetClass) {
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
}
