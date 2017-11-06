package com.basicpencil.screenshot.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.basicpencil.screenshot.ui.MainActivity

class NotificationBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private val LOG_TAG = NotificationBroadcastReceiver::class.qualifiedName
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            MainActivity.ACTION_NOTIFICATION_CANCELED -> {
                Log.w(LOG_TAG, intent.action)
            }
        }
    }
}