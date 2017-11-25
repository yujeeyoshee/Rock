package com.basicpencil.screenshot.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.basicpencil.screenshot.ui.MainActivity

class NotificationBroadcastReceiver : BroadcastReceiver() {
    companion object {
        private val LOG_TAG = NotificationBroadcastReceiver::class.qualifiedName
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.w(LOG_TAG, "NotificationBroadcastReceiver.onReceive()")

        when (intent.action) {
            MainActivity.ACTION_NOTIFICATION_CLICKED -> {
                Log.w(LOG_TAG, intent.action)
                val intent = Intent(context, NotificationService::class.java)
                context.startService(intent)
            }

            MainActivity.ACTION_NOTIFICATION_CANCELED -> {
                Log.w(LOG_TAG, intent.action)
                val intent = Intent(MainActivity.LOCAL_NOTIFICATION_DISMISSED)

                LocalBroadcastManager.getInstance(context.applicationContext)
                        .sendBroadcast(intent)
            }
        }
    }
}