package com.basicpencil.screenshot.receiver

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.basicpencil.screenshot.ui.MainActivity

class NotificationIntentService : IntentService("NotificationIntentService") {
    companion object {
        private val LOG_TAG = NotificationIntentService::class.qualifiedName
    }

    override fun onHandleIntent(intent: Intent) {
        Log.w(LOG_TAG, "touched")

        when (intent.action) {
            MainActivity.ACTION_NOTIFICATION_CLICKED -> {
                Log.w(LOG_TAG, intent.action)
            }
        }
    }
}
