package com.github.aakumykov.kotlin_playground.shortcuts_parser.utils

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ResourceResolver(private val packageName: String, private val resources: Resources) {

    @StringRes
    fun getStringResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, STRING, packageName)
    }

    @DrawableRes
    fun getDrawableResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, DRAWABLE, packageName)
    }

    companion object {
        const val STRING = "string"
        const val DRAWABLE = "drawable"
    }
}