package com.base.project.camera

import com.base.project.camera.utils.Photo
import java.io.Serializable

/**
 * 拍照或从相册选择图片回掉接口
 */
interface OnPicturePathListener : Serializable{
    /**
     * @param photo  选择的图片文件对象（包含原始文件，压缩文件，裁剪文件）
     */
    fun onPhoto(photo: Photo)
}