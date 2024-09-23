package com.github.aakumykov.kotlin_playground

import androidx.annotation.StringRes

fun string(@StringRes strRes: Int): String {
    return targetContext.getString(strRes)
}