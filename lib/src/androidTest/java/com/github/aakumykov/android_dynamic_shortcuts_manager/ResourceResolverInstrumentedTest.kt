package com.github.aakumykov.android_dynamic_shortcuts_manager

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.aakumykov.android_dynamic_shortcuts_manager.common.packageName
import com.github.aakumykov.android_dynamic_shortcuts_manager.common.resources
import com.github.aakumykov.android_dynamic_shortcuts_manager.shortcuts_parser.utils.ResourceResolver
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResourceResolverInstrumentedTest {

    private lateinit var resourceResolver: ResourceResolver

    @Before
    fun prepareResourceResolver() {
        resourceResolver = ResourceResolver(packageName, resources)
    }

    @Test
    fun resolve_string_resource_without_error() {
        assertEquals("Динамические ярлыки", resourceResolver.getStringResourceByName("app_name"))
    }
}