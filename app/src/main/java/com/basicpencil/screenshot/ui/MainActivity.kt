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

class MainActivity: AppCompatActivity() {

    companion object {
        val ACTION_NOTIFICATION_CANCELED = "notification_canceled"
        private val LOG_TAG = MainActivity::class.qualifiedName
        private val NOTIFY_ID = 54321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        val enableNotificationSwitch = findViewById<Switch>(R.id.enable_notification_switch)
        enableNotificationSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val onDismissedIntent = createOnDismissedPendingIntent()
                val notificationPayload = Notification.Builder(this)
                        .setContentTitle("Hello")
                        .setContentText("world")
                        .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                        .setDeleteIntent(onDismissedIntent)
                        .build()
                notificationManager?.notify(NOTIFY_ID, notificationPayload)
                Log.w(LOG_TAG, "on")
            } else {
                notificationManager?.cancel(NOTIFY_ID)
                Log.w(LOG_TAG, "off")
            }
        }
    }

    private fun createOnDismissedPendingIntent(): PendingIntent {
        val intent = Intent(this, NotificationBroadcastReceiver::class.java)
        intent.action = ACTION_NOTIFICATION_CANCELED
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}
