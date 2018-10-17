package com.base.muslim.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.base.library.retrofit_rx.RxRetrofitApp
import com.base.muslim.camera.capturedialog.ListenerItem
import com.base.muslim.camera.capturedialog.PictureCaptureDialog
import com.base.muslim.camera.utils.Photo
import com.base.muslim.compression.PictureCompression
import java.io.Serializable
import java.lang.ref.WeakReference

/**
 * 图片获取（拍照或从相册）
 */
object PictureCapture :OnPicturePathListener ,Serializable{
    /*是否需要剪裁*/
    var needCrop: Boolean = false
    const val BUNDLE_KEY = "ListenerItem"
    private val listenerItem by lazy { ListenerItem() }
    private val bundle by lazy { Bundle() }
    private lateinit var weakReference: WeakReference<OnPicturePathListener>


    /**
     * 获取图片，通过拍照或从相册选择 （不经过压缩）
     * @param context
     * @param listener   未压缩的图片文件回掉接口
     * @param needCrop   是否需要剪裁
     */
    fun getPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean) {
        this.needCrop = needCrop
        weakReference = WeakReference(listener)
        listenerItem.listener = this
        showDialog(context)
    }

    /**
     * 获取图片，通过拍照或从相册选择 （压缩过的图片）
     * @param context
     * @param listener   压缩过的图片文件回掉接口
     * @param needCrop   是否需要剪裁
     */
    fun getCompressionPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean) {
        this.needCrop = needCrop
        weakReference = WeakReference(listener)
        listenerItem.listener = this
        showDialog(context)
    }

    /**
     * 使用getCompressionPicture时在此回掉方法中去压缩图片
     */
    override fun onPhoto(photo: Photo) {
        PictureCompression.compressionPictureRx(RxRetrofitApp.getApplication(), photo, weakReference.get()!!)
    }

    /**
     *显示选择Camera 或 Album 的Dialog
     */
    private fun showDialog(context: Context) {
        var intent = Intent(context, PictureCaptureDialog::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        bundle.putSerializable(BUNDLE_KEY, listenerItem)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

}