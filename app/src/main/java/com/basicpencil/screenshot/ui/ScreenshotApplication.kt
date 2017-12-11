package com.basicpencil.screenshot.ui

import android.app.Application
import android.content.Context

class ScreenshotApplication : Application() {
    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
