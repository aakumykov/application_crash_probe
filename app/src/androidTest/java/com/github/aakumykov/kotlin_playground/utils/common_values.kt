package com.github.aakumykov.kotlin_playground.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

val targetContext: Context get() = InstrumentationRegistry.getInstrumentation().targetContext
