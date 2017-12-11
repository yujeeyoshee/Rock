package com.basicpencil.screenshot.util

import android.content.Context
import com.basicpencil.screenshot.ui.ScreenshotApplication

object PrefUtil {
    fun writeBoolean(key: String, value: Boolean) {
        val pref = ScreenshotApplication.context?.getSharedPreferences(Constants.APP_PREF_FILE, Context.MODE_PRIVATE)
        pref!!.edit().putBoolean(key, value).apply()
    }

    fun readBoolean(key: String) : Boolean {
        val pref = ScreenshotApplication.context?.getSharedPreferences(Constants.APP_PREF_FILE, Context.MODE_PRIVATE)
        return pref!!.getBoolean(key, false)
    }
}
