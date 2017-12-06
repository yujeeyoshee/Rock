package com.basicpencil.screenshot.ui

import android.Manifest
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.basicpencil.screenshot.R
import java.io.File
import java.io.FileOutputStream
import java.util.*
import android.content.Intent
import android.net.Uri
import android.view.View.MeasureSpec
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.view.ViewGroup



class ScreenshotActivity : AppCompatActivity() {
    companion object {
        private val LOG_TAG = ScreenshotActivity::class.qualifiedName

        // Storage Permissions
        private val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screenshot)

        verifyStoragePermissions();
    }

    private fun verifyStoragePermissions() {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
        } else {
            takeScreenshot()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_EXTERNAL_STORAGE) {
                takeScreenshot()
            }
        }
    }

    private fun takeScreenshot() {
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"
            Log.w(LOG_TAG, mPath)

            val v1 = getWindow().getDecorView().getRootView()
            v1.setDrawingCacheEnabled(true)

            // this is the important code :)
            // Without it the view will have a dimension of 0,0 and the bitmap will be null
            v1.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
            v1.layout(0, 0, v1.getMeasuredWidth(), v1.getMeasuredHeight())
            v1.buildDrawingCache(true)

            if (v1 == null) Log.w(LOG_TAG, "v1 is null")
            val bitmap = Bitmap.createBitmap(v1.getDrawingCache())
            v1.setDrawingCacheEnabled(false) // clear drawing cache

            val imageFile = File(mPath)

            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            openScreenshot(imageFile)
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }

    private fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        startActivity(intent)
    }
}
