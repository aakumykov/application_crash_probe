package com.github.aakumykov.kotlin_playground

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.github.aakumykov.android_dynamic_shortcuts_manager.DefaultShortcutsCreator
import com.github.aakumykov.kotlin_playground.extensions.getErrorMessage

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createDefaultShortcutsOnFirstInstall()
    }

    private fun createDefaultShortcutsOnFirstInstall() {
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (getBoolean(FIST_INSTALLATION, true)) {
                createDefaultShortcutsSafely()
                edit().putBoolean(FIST_INSTALLATION, false).apply()
            }
        }
    }

    private fun createDefaultShortcutsSafely() {
        try { createDefaultShortcuts(this) }
        catch (e: Exception) { Log.e(TAG, e.getErrorMessage(), e) }
    }

    companion object {

        val TAG: String = App::class.java.simpleName
        private const val FIST_INSTALLATION = "FIST_INSTALLATION"

        @Throws(Exception::class)
        fun createDefaultShortcuts(context: Context) {
            DefaultShortcutsCreator.getDefault(context).initShortcuts(R.raw.shortcuts)
        }
    }
}