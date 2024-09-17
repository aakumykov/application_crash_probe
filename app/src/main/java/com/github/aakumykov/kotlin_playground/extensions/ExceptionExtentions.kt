package com.github.aakumykov.kotlin_playground.extensions

fun Throwable.getErrorMessage(): String = message ?: javaClass.simpleName