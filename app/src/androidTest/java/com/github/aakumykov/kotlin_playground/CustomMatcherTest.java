package com.github.aakumykov.kotlin_playground;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.model.Shortcut;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CustomMatcherTest {

    public static Matcher<Object> withItemValue(final String value) {
        return new BoundedMatcher<Object, Shortcut>(Shortcut.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has value " + value);
            }

            @Override
            public boolean matchesSafely(Shortcut item) {
                return item.shortcutId.toUpperCase().equals(String.valueOf(value));
            }
        };
    }
}
