package com.basicpencil.screenshot.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.basicpencil.screenshot.R

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = MainActivity::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enableNotificationSwitch = findViewById<Switch>(R.id.enable_notification_switch)
        enableNotificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.w(LOG_TAG, "on")
            } else {
                Log.w(LOG_TAG, "off")
            }

        }
    }
}
