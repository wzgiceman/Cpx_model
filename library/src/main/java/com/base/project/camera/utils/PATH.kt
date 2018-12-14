package com.base.project.camera.utils

import android.annotation.SuppressLint
import android.content.Context
import java.lang.ref.WeakReference

@SuppressLint("StaticFieldLeak")
/**
 * 相关路径信息类
 *
 * @author wyf
 */
object PATH {

    private lateinit var weakReference: WeakReference<Context>

    fun initialize(context: Context) {
        weakReference = WeakReference(context)
        FILE.createDir(imageSaveDir)
    }

    /**
     * 图片另存为SDCard/Android/data/目录
     *
     * @return
     */
    val imageSaveDir: String
        get() = weakReference.get()!!.getExternalFilesDir(null).toString() + "/photo/"

}
