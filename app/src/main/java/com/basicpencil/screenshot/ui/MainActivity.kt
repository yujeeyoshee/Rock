package com.basicpencil.screenshot.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.basicpencil.screenshot.R
import com.basicpencil.screenshot.receiver.NotificationBroadcastReceiver
import android.app.PendingIntent
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.basicpencil.screenshot.receiver.NotificationIntentService

class MainActivity: AppCompatActivity() {
    companion object {
        val ACTION_NOTIFICATION_CANCELED = "action_notification_canceled"
        val ACTION_NOTIFICATION_CLICKED = "action_notification_clicked"
        val LOCAL_NOTIFICATION_DISMISSED = "local_notification_dismissed"
        private val LOG_TAG = MainActivity::class.qualifiedName
        private val NOTIFY_ID = 54321
    }

    private var enableNotificationSwitch: Switch? = null;

    private val localBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.w(LOG_TAG, "yay")
            when (intent.action) {
                LOCAL_NOTIFICATION_DISMISSED -> {
                    Log.w(LOG_TAG, "woohoo locally")
                    enableNotificationSwitch?.setChecked(false)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        enableNotificationSwitch = findViewById<Switch>(R.id.enable_notification_switch)
        enableNotificationSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val notificationPayload = Notification.Builder(this)
                        .setContentTitle(getString(R.string.easiest_screenshot))
                        .setContentText(getString(R.string.notification_content))
                        .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                        .setDeleteIntent(createOnDismissPendingIntent())
                        .setContentIntent(createOnClickPendingIntent())
                        .build()
                notificationManager?.notify(NOTIFY_ID, notificationPayload)
                Log.w(LOG_TAG, "on")
            } else {
                notificationManager?.cancel(NOTIFY_ID)
                Log.w(LOG_TAG, "off")
            }
        }

        LocalBroadcastManager.getInstance(applicationContext)
                .registerReceiver(localBroadcastReceiver, IntentFilter(LOCAL_NOTIFICATION_DISMISSED))
    }

    private fun createOnDismissPendingIntent(): PendingIntent {
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        intent.action = ACTION_NOTIFICATION_CANCELED
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun createOnClickPendingIntent(): PendingIntent {
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        intent.action = ACTION_NOTIFICATION_CLICKED
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private fun createOnClickPendingIntent_IntentService(): PendingIntent {
        val intent = Intent(this, NotificationIntentService::class.java)
        intent.action = ACTION_NOTIFICATION_CLICKED
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override fun onDestroy() {
        // Note that onDestroy is hardly ever called, but the receiver will be killed when app is killed
        // - Chose not to register/unregister localBroadcastReceiver on onResume/onPause because
        //   even when the app in the background due to home key press, we still want notification
        //   dismissal to disable enableNotificationSwitch
        LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(localBroadcastReceiver)
        super.onDestroy()
    }
}
