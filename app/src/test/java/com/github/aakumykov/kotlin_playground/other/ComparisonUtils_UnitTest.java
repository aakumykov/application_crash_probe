package com.github.aakumykov.kotlin_playground.other;

import static com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.other.ComparisonUtils.bothNullOrEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComparisonUtils_UnitTest {

    private static final Object SOME_OBJECT = new Object();
    private static final Object OTHER_OBJECT = new Object();

    @Test
    public void when_compare_null_with_null_then_true() {
        assertTrue(bothNullOrEquals(null,null));
    }

    @Test
    public void when_compare_non_null_with_null_then_false() {
        assertFalse(bothNullOrEquals(SOME_OBJECT,null));
    }

    @Test
    public void when_compare_null_with_non_null_then_false() {
        assertFalse(bothNullOrEquals(null, SOME_OBJECT));
    }

    @Test
    public void when_compare_same_objects_when_true() {
        assertTrue(bothNullOrEquals(SOME_OBJECT, SOME_OBJECT));
    }

    @Test
    public void when_compare_different_objects_when_false() {
        assertFalse(bothNullOrEquals(SOME_OBJECT, OTHER_OBJECT));
    }
}
