package com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.other;

import androidx.annotation.Nullable;

public class ComparisonUtils {

    public static boolean bothNullOrEquals(@Nullable Object o1, @Nullable Object o2) {
        if (null == o1 && null == o2)
            return true;
        else if (notBothNull(o1,o2))
            return false;
        else
            return o1.equals(o2);
    }

    public static boolean notBothNull(@Nullable Object o1, @Nullable Object o2) {
        if (null == o1 && null != o2) return true;
        else return null != o1 && null == o2;
    }
}
