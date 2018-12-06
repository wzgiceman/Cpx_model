package com.base.muslim.share.common.util

import android.graphics.Bitmap
import android.net.Uri
import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.library.utils.utilcode.util.FileUtils
import java.io.File
import java.io.FileOutputStream

/**
 * Description:
 * Share工具类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
object ShareUtils {
    /**
     * Bitmap转成Uri，保存在app内部存储的 share_temp_System.currentTimeMillis().png 中
     */
    fun bitmap2Uri(image: Bitmap): Uri {
        val fileDir = RxRetrofitApp.getApplication().getExternalFilesDir(null)
        fileDir.mkdirs()
        val file = File(fileDir, "share_temp_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()
        return Uri.fromFile(file)
    }

    /**
     * 删除分享时用的临时图片
     */
    fun clearShareTempPictures() {
        synchronized(this) {
            val fileDir = RxRetrofitApp.getApplication().getExternalFilesDir(null)
                    .apply { mkdirs() }
            fileDir
                    .list()
                    .map {
                        if (it.startsWith("share_temp_")) {
                            FileUtils.delete("$fileDir${File.separatorChar}$it")
                        }
                    }
        }
    }
}