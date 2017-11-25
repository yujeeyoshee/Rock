package com.basicpencil.screenshot.receiver

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.basicpencil.screenshot.ui.MainActivity

class NotificationIntentService : IntentService("NotificationIntentService") {
    companion object {
        private val LOG_TAG = NotificationIntentService::class.qualifiedName
    }

    override fun onHandleIntent(intent: Intent) {
        Log.w(LOG_TAG, "NotificationIntentService.onHandleIntent()")

        when (intent.action) {
            MainActivity.ACTION_NOTIFICATION_CLICKED -> {
                Log.w(LOG_TAG, intent.action)
            }
            MainActivity.ACTION_NOTIFICATION_CANCELED -> {
                Log.w(LOG_TAG, intent.action)
                val intent = Intent(MainActivity.LOCAL_NOTIFICATION_DISMISSED)
                LocalBroadcastManager.getInstance(applicationContext)
                        .sendBroadcast(intent)
            }
        }
    }
}
