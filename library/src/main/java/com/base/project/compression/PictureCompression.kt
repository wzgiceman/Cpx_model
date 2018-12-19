package com.base.project.compression

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.base.library.utils.utilcode.util.LogUtils
import com.base.project.camera.OnPicturePathListener
import com.base.project.camera.utils.PATH
import com.base.project.camera.utils.Photo
import com.base.project.camera.utils.PictureType
import com.base.project.compression.luban.Luban
import com.base.project.compression.luban.OnCompressListener
import com.base.project.compression.minterface.OnCompressionPicturesListener
import com.base.project.compression.minterface.OnPictureCompressionListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

/**
 * 图片压缩类
 */
@SuppressLint("CheckResult")
object PictureCompression {

    private fun initialize(context: Context) {
        PATH.initialize(context)
    }

    private val temPictureFile by lazy {
        File(PATH.imageSaveDir)
    }

    private fun getTargetDir() = temPictureFile.toString()

    private fun getRename() = "compression-" + getCurrentTime() + ".jpg"

    //使用rxjava调用压缩的重命名
    private fun getRenameRx() = "compression-rx" + getCurrentTime() + ".jpg"

    private fun getCurrentTime() = System.currentTimeMillis()


    /**
     * 图片压缩
     * @param context
     * @param path
     * @param listener 回掉接口
     */
    fun compressionPicture(context: Context, path: String, listener: OnPictureCompressionListener) {
        initialize(context)
        Luban.with(context)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .filter { !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif")) }
                .setRenameListener { getRename() }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {

                    }

                    override fun onSuccess(file: File) {
                        listener.onPictureFile(file)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtils.d(PictureCompression::class.java, "onError:" + e!!.message)
                    }
                }).launch()
    }

    /**
     * 图片压缩
     * @param context
     * @param file
     * @param listener 回掉接口
     */
    fun compressionPicture(context: Context, file: File, listener: OnPictureCompressionListener) {
        initialize(context)
        Luban.with(context)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .setRenameListener { getRename() }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {

                    }

                    override fun onSuccess(file: File) {
                        listener.onPictureFile(file)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtils.d(PictureCompression::class.java, "onError:" + e!!.message)
                    }
                }).launch()
    }

    /**
     * 图片压缩
     * @param context
     * @param uri
     * @param listener 回掉接口
     */
    fun compressionPicture(context: Context, uri: Uri, listener: OnPictureCompressionListener) {
        initialize(context)
        Luban.with(context)
                .load(uri)
                .ignoreBy(100)
                .setTargetDir(getTargetDir())
                .setFocusAlpha(false)
                .setRenameListener { getRename() }
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                    }

                    override fun onSuccess(file: File) {
                        listener.onPictureFile(file)
                    }

                    override fun onError(e: Throwable?) {
                        LogUtils.d(PictureCompression::class.java, "onError:" + e!!.message)
                    }
                }).launch()
    }

    /**
     * 图片压缩  （使用RxJava）
     * @param context
     * @param path  图片路径
     * @param listener 回掉接口
     */
    fun compressionPictureRx(context: Context, path: String, listener: OnPictureCompressionListener) {
        Observable.just(path)
                .subscribeOn(Schedulers.newThread())
                .map { it ->
                    Luban.with(context)
                            .setTargetDir(getTargetDir())
                            .setRenameListener { getRenameRx() }
                            .load(it)
                            .get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    for (file in it)
                        listener.onPictureFile(file)
                }
    }

    /**
     * 图片压缩  （使用RxJava）
     * @param context
     * @param file
     * @param listener 回掉接口
     */
    fun compressionPictureRx(context: Context, file: File, listener: OnPictureCompressionListener) {
        Observable.just(file)
                .subscribeOn(Schedulers.newThread())
                .map { it ->
                    Luban.with(context)
                            .setTargetDir(getTargetDir())
                            .setRenameListener { getRenameRx() }
                            .load(it)
                            .get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    for (file in it)
                        listener.onPictureFile(file)
                }
    }

    /**
     * 图片压缩  （使用RxJava）
     * @param context
     * @param file
     * @param listener 回掉接口
     */
    fun compressionPictureRx(context: Context, uri: Uri, listener: OnPictureCompressionListener) {
        Observable.just(uri)
                .subscribeOn(Schedulers.newThread())
                .map { it ->
                    Luban.with(context)
                            .setTargetDir(getTargetDir())
                            .setRenameListener { getRenameRx() }
                            .load(it)
                            .get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    for (file in it)
                        listener.onPictureFile(file)
                }


    }

    /**
     * 图片压缩  （使用RxJava）
     * @param context
     * @param photo  photo对象
     * @param listener 回掉接口
     */
    fun compressionPictureRx(context: Context, photo: Photo, listener: OnPicturePathListener) {
        if (photo.pictureType == PictureType.CROP) {
            photo.compressionFile = photo.cropFile
            listener.onPhoto(photo)
            return
        }
        Observable.just(photo.originalFile)
                .subscribeOn(Schedulers.newThread())
                .map { it ->
                    Luban.with(context)
                            .setTargetDir(getTargetDir())
                            .setRenameListener { getRenameRx() }
                            .load(it)
                            .get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    for (file in it)
                        photo.compressionFile = file
                    listener.onPhoto(photo)
                }
    }

    /**
     * 多张图片压缩  （使用RxJava）
     * @param context
     * @param photos  装有图片的集合  （集合的泛型可以是  File、String和Uri 类型）
     * @param listener 回掉接口
     */
    fun compressionPictureRx(context: Context, photos: List<*>, listener: OnCompressionPicturesListener) {
        Observable.just(photos)
                .subscribeOn(Schedulers.newThread())
                .map { it ->
                    Luban.with(context)
                            .setTargetDir(getTargetDir())
                            .setRenameListener { getRenameRx() }
                            .load(it)
                            .get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    listener.onPictureFiles(it)
                }
    }

}