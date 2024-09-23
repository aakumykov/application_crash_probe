package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.other;

import androidx.annotation.Nullable;

public class ComparisonUtils {

    public static boolean bothNullOrEquals(@Nullable Object thisField, @Nullable Object comparedField) {
        if (null == thisField && null == comparedField)
            return true;
        else if (null == thisField)
            return false;
        else if (null == comparedField)
            return false;
        else
            return thisField.equals(comparedField);
    }
}
