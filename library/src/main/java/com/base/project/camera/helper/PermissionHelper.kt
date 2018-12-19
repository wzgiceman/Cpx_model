package com.base.project.camera.helper

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by cpx on 2018/4/1.
 * 权限申请类
 */
object PermissionHelper {

    const val REQUESTCODE_CAMERA = 0x01
    const val REQUESTCODE_ALBUM = 0x02


    fun checkSelfPermission(activity: Activity, permissions: Array<String>): List<String> {
        val list = ArrayList<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED) {
                list.add(it)
            }

        }
        return list
    }

    fun requestPermissions(activity: Activity, requestCode: Int, permissions: List<String>) {
        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): Boolean {
        val allPermissions = grantResults.all {
            it == PackageManager.PERMISSION_GRANTED
        }
        return allPermissions
    }

}
