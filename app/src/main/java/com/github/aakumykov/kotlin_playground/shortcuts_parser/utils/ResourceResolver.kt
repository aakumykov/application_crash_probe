package com.github.aakumykov.kotlin_playground.shortcuts_parser.utils

import android.content.res.Resources
import android.content.res.Resources.NotFoundException
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ResourceResolver(private val packageName: String, private val resources: Resources) {

    @Throws(NotFoundException::class)
    @StringRes
    fun getStringResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, STRING, packageName).let { resource ->
            if (0 == resource) throw NotFoundException("String resource '$stringName' not found in package '$packageName'.")
            else resource
        }
    }

    @Throws(NotFoundException::class)
    @DrawableRes
    fun getDrawableResourceByName(stringName: String): Int {
        return resources.getIdentifier(stringName, DRAWABLE, packageName).let { resource ->
            if (0 == resource) throw NotFoundException("Drawable resource '$stringName' not found in package '$packageName'.")
            else resource
        }
    }

    /*fun getStringResourceByName(stringName: String): Result<Int> {
        return resources.getIdentifier(stringName, STRING, packageName).let {  res ->
            if (0 == res) Result.failure(NotFoundException("String resource '$stringName' not found in package '$packageName'."))
            else Result.success(res)
        }
    }*/

    /*fun getDrawableResourceByName(stringName: String): Result<Int> {
        return resources.getIdentifier(stringName, DRAWABLE, packageName).let {  res ->
            if (0 == res) Result.failure(NotFoundException("Drawable resource '$stringName' not found in package '$packageName'."))
            else Result.success(res)
        }
    }*/

    companion object {
        const val STRING = "string"
        const val DRAWABLE = "drawable"
    }
}