package com.github.aakumykov.kotlin_playground.test_utils

import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry

val targetContext: Context get() = InstrumentationRegistry.getInstrumentation().targetContext

val resources: Resources = targetContext.resources

val packageName: String = targetContext.packageName