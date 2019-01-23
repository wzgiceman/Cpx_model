package com.base.project.camera

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.project.camera.capturedialog.ListenerItem
import com.base.project.camera.capturedialog.PictureCaptureDialog
import com.base.project.camera.utils.Photo
import com.base.project.compression.PictureCompression
import java.io.Serializable
import java.lang.ref.WeakReference

/**
 * 图片获取（拍照或从相册）
 */
object PictureCapture : OnPicturePathListener, Serializable {
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
    fun getPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean = false) {
        this.needCrop = needCrop
        weakReference = WeakReference(listener)
        listenerItem.listener = this
        showDialog(context)
    }

    /**
     * 获取图片，通过拍照或从相册选择 （不经过压缩）
     * @param context
     * @param listener   未压缩的图片文件回掉接口
     * @param needCrop   是否需要剪裁
     * @param chooseTake   是否直接选择拍照方式，不需要弹框选择 1拍照 2图片
     */
    fun getPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean = false, chooseTake: Int = -1) {
        this.needCrop = needCrop
        weakReference = WeakReference(listener)
        listenerItem.listener = this
        showDialog(context, chooseTake)
    }

    /**
     * 获取图片，通过拍照或从相册选择 （压缩过的图片）
     * @param context
     * @param listener   压缩过的图片文件回掉接口
     * @param needCrop   是否需要剪裁
     */
    fun getCompressionPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean = false) {
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
     * @param chooseTake   是否直接选择拍照方式，不需要弹框选择 1拍照 2图片
     */
    fun getCompressionPicture(context: Context, listener: OnPicturePathListener, needCrop: Boolean = false, chooseTake: Int = -1) {
        this.needCrop = needCrop
        weakReference = WeakReference(listener)
        listenerItem.listener = this
        showDialog(context, chooseTake)
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
    private fun showDialog(context: Context, chooseTake: Int = -1) {
        var intent = Intent(context, PictureCaptureDialog::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        bundle.putSerializable(BUNDLE_KEY, listenerItem)
        intent.putExtra("chooseTake", chooseTake)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }

}