package com.github.aakumykov.kotlin_playground

class Qwerty {
    companion object {
        fun work(): String = Qwerty::class.java.simpleName + "-MOCK"
    }
}