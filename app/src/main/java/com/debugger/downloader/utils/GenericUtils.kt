package com.debugger.downloader.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.util.Log
import android.widget.Toast


object GenericUtils {
    fun showToast(context: Context, strError: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, strError, length).show()
    }

    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        val MIN_OPENGL_VERSION = 3.0
        val openGlVersionString = (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e("Error", "Sceneform requires OpenGL ES 3.0 later")
            showToast(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
            activity.finish()
            return false
        }
        return true
    }
}