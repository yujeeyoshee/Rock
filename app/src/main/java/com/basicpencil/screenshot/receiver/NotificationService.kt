package com.basicpencil.screenshot.receiver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class NotificationService : Service() {
    companion object {
        private val LOG_TAG = NotificationService::class.qualifiedName
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.w(LOG_TAG, "NotificationService.onStartCommand()")

        // Be responsible and stop the service when done
        stopSelf();

        return START_STICKY;
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.w(LOG_TAG, "NotificationService.onBind()")
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}
