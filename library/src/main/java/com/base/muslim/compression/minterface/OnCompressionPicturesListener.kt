package com.base.muslim.compression.minterface

import java.io.File

/**
 * 图片压缩回掉接口 （压缩多张图片）
 */
interface OnCompressionPicturesListener {
    /**
     * 回掉方法，将压缩后的图片集合返回
     *
     * @param pictures 装有压缩成功后的图片文件的集合
     */
    fun onPictureFiles(pictures: List<File>)
}