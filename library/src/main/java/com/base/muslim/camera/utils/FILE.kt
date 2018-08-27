package com.base.muslim.camera.utils

import java.io.File


/**
 * 文件简单操作辅助类
 *
 * @author wl
 */
object FILE {
    /**
     * 创建目录，整个路径上的目录都会创建
     *
     * @param path
     */
    fun createDir(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }


}
