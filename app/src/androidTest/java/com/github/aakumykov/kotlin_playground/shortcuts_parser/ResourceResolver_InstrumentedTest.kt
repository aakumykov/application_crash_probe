package com.github.aakumykov.kotlin_playground.shortcuts_parser

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.aakumykov.android_dynamic_shortcuts_manager.R
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import com.github.aakumykov.kotlin_playground.common.packageName
import com.github.aakumykov.kotlin_playground.common.resources
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceResolver_InstrumentedTest {

    private val resourceResolver: ResourceResolver = ResourceResolver(packageName, resources)


    @Test
    fun resolve_string_resource_without_error() {
        assertEquals(
            R.string.test_string,
            resourceResolver.getStringResourceByName("test_string"))
    }

    @Test
    fun resolve_drawable_resource_name_to_int() {
        assertEquals(
            R.drawable.ic_android_black_24dp,
            resourceResolver.getDrawableResourceByName("ic_android_black_24dp"))
    }
}