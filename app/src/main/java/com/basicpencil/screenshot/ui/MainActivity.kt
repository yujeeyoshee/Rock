package com.basicpencil.screenshot.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.basicpencil.screenshot.R

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = MainActivity::class.qualifiedName
        val NOTIFY_ID = 54321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notifactionManager = getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        val enableNotificationSwitch = findViewById<Switch>(R.id.enable_notification_switch)
        enableNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val notificationPayload = Notification.Builder(this)
                        .setContentTitle("Hello")
                        .setContentText("world")
                        .setSmallIcon(R.drawable.ic_stat_onesignal_default)
                        .build()
                notifactionManager?.notify(NOTIFY_ID, notificationPayload);
                Log.w(LOG_TAG, "on")
            } else {
                notifactionManager?.cancel(NOTIFY_ID);
                Log.w(LOG_TAG, "off")
            }

        }
    }
}
