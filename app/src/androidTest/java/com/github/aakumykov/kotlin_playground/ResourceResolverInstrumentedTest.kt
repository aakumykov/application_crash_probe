package com.github.aakumykov.kotlin_playground

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import com.github.aakumykov.kotlin_playground.utils.packageName
import com.github.aakumykov.kotlin_playground.utils.resources
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceResolverInstrumentedTest {

    private val resourceResolver: ResourceResolver = ResourceResolver(packageName, resources)


    @Test
    fun resolve_string_resource_without_error() {
        assertEquals(
            com.github.aakumykov.kotlin_playground.R.string.app_name,
            resourceResolver.getStringResourceByName("Динамические ярлыки"))
    }

    @Test
    fun resolve_drawable_resource_name_to_int() {
        assertEquals(
            com.github.aakumykov.kotlin_playground.R.drawable.settings,
            resourceResolver.getDrawableResourceByName("settings"))
    }
}