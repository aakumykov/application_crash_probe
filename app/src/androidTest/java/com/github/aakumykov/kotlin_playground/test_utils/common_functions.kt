package com.github.aakumykov.kotlin_playground.test_utils

import androidx.annotation.StringRes

fun string(@StringRes strRes: Int): String {
    return targetContext.getString(strRes)
}