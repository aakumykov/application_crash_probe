package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils;

import android.content.res.Resources;

public class ResourceResolver {

    private static final String RES_TYPE_STRING = "string";
    private static final String RES_TYPE_DRAWABLE = "drawable";

    private final String packageName;
    private final Resources resources;

    public ResourceResolver(String packageName, Resources resources) {
        this.packageName = packageName;
        this.resources = resources;
    }

    public int getStringResourceByName(String stringResourceName) throws Resources.NotFoundException {
        int resId = resources.getIdentifier(stringResourceName, RES_TYPE_STRING, packageName);
        if (0 == resId) throw new Resources.NotFoundException("String resource '"+stringResourceName+"' not found in package '"+packageName+"'.");
        else return resId;
    }

    public int getDrawableResourceByName(String drawableResourceName) throws Resources.NotFoundException {
        int resId = resources.getIdentifier(drawableResourceName, RES_TYPE_DRAWABLE, packageName);
        if (0 == resId) throw new Resources.NotFoundException("Drawable resource '"+drawableResourceName+"' not found in package '"+packageName+"'.");
        else return resId;
    }
}
