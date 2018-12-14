package com.base.project.camera.capturedialog

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.base.library.R
import com.base.library.rxPermissions.RxPermissions
import com.base.project.base.activity.BaseActivity
import com.base.project.camera.OnPicturePathListener
import com.base.project.camera.PictureCapture
import com.base.project.camera.helper.PermissionHelper
import com.base.project.camera.helper.PictureHelper
import com.base.project.camera.utils.PATH
import com.base.project.camera.utils.Photo
import com.base.project.camera.utils.PictureType
import kotlinx.android.synthetic.main.dialog_picture_capture.*
import java.io.File

/**
 * 选择相机或相册的弹窗，同时处理权限申请以及拍照或选择照片结果
 */
class PictureCaptureDialog : BaseActivity() {

    private var cropWith = 480
    private var cropHeiht = 480
    private val photo by lazy { Photo() }
    lateinit var listener: OnPicturePathListener

    private val temPictureFile by lazy {
        File(PATH.imageSaveDir + "camera-photo-" + System.currentTimeMillis() + ".jpg")
    }

    private val pictureFile by lazy {
        File(PATH.imageSaveDir + "crop-photo-" + System.currentTimeMillis() + ".jpg")
    }

    override fun initActivity() {
        RxPermissions.getInstance(this).request(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) { //有权限
                        super.initActivity()
                    } else {
                        Toast.makeText(this, getString(R.string.please_turn_on_camera_permissions), Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
    }
    override fun layoutId() =R.layout.dialog_picture_capture

    override fun initData() {
        PATH.initialize(this)
        val intent = intent
        val bundle = intent.extras
        val listenerItem = bundle.getSerializable(PictureCapture.BUNDLE_KEY) as ListenerItem
        listener = listenerItem.listener
    }

    override fun initView() {
        setWidgetClick()
        setLayoutParams()
    }

    private fun setLayoutParams() {
        var width = resources.displayMetrics.widthPixels
        var height = resources.displayMetrics.heightPixels
        var params = FrameLayout.LayoutParams(width, height)
        layout.layoutParams = params
    }

    private fun setWidgetClick() {
        tv_camera.setOnClickListener {
            takePicture()
            setVisible()
        }

        tv_album.setOnClickListener {
            getPictureFromAlbum()
            setVisible()
        }
        tv_cancel.setOnClickListener { finish() }
    }

    private fun setVisible() {
        layout.visibility = View.INVISIBLE
    }

    private fun requestPermission() {
        RxPermissions.getInstance(this).request(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) { //有权限
                        layout.visibility = View.VISIBLE
                    }
                }
    }

    /**
     * 打开相机拍照获取图片
     */
    private fun takePicture() {
        val noPermissions = PermissionHelper.checkSelfPermission(this, arrayOf(android.Manifest.permission.CAMERA))
        if (noPermissions.isEmpty()) {
            PictureHelper.takePicture(this, temPictureFile)
        } else {
            requestPermission()
        }
    }

    /**
     * 从相册获取图片
     */
    private fun getPictureFromAlbum() {
        val noPermissions = PermissionHelper.checkSelfPermission(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE))
        if (noPermissions.isEmpty()) {
            PictureHelper.openAlbum(this)
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            val result = PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if (!result) return
            when (requestCode) {
                PermissionHelper.REQUESTCODE_CAMERA -> takePicture()
                PermissionHelper.REQUESTCODE_ALBUM -> getPictureFromAlbum()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    PictureHelper.REQUESTCODE_CAMERA -> {
                        dealWithCameraPicture()
                    }
                    PictureHelper.REQUESTCODE_ALBUM -> {
                        dealWithAlbumPicture(data)
                    }
                    PictureHelper.REQUESTCODE_CROP -> {
                        dealWithCropPicture(data)
                    }
                }
            } else {
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 处理拍照的结果
     */
    private fun dealWithCameraPicture() {
        try {
            val desUri: Uri = Uri.fromFile(pictureFile)
            val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, this.packageName + ".provider", temPictureFile)
            } else {
                Uri.fromFile(temPictureFile)
            }
            photo.originalFile = File(temPictureFile.toString())
            photo.pictureType = PictureType.CAMERA
            if (PictureCapture.needCrop) {
                PictureHelper.cropImageUri(this, imageUri, desUri, 1, 1, cropWith, cropHeiht)
            } else {
                sendImagePath(null, PictureType.CAMERA)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 处理从相册选择照片的结果
     */
    private fun dealWithAlbumPicture(data: Intent?) {
        try {
            val pictureUri: Uri = data!!.data
            val desUri: Uri = Uri.fromFile(pictureFile)
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(pictureUri,
                    filePathColumn, null, null, null)//从系统表中查询指定Uri对应的照片
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val path = cursor.getString(columnIndex)  //获取照片路径
            cursor.close()
            photo.originalFile = File(path)
            photo.pictureType = PictureType.ALBUM
            if (PictureCapture.needCrop) {
                PictureHelper.cropImageUri(this, pictureUri, desUri, 1, 1, cropWith, cropHeiht)
            } else {
                sendImagePath(Uri.parse(path), PictureType.ALBUM)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 处理裁剪后的图片并返回bitmap
     */
    private fun dealWithCropPicture(data: Intent?) {
        try {
            val uri: Uri = if (data != null && data.data != null) {
                data.data
            } else {
                Uri.fromFile(pictureFile)
            }
            sendImagePath(uri, PictureType.CROP)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendImagePath(uri: Uri?, type: PictureType) {
        when (type) {
            PictureType.CROP -> {
                val path = uri.toString().substring(uri.toString().indexOf("/") + 1, uri.toString().lastIndex + 1)
                photo.cropFile = File(path)
                photo.pictureType = PictureType.CROP
            }
            PictureType.CAMERA -> {
                val path = temPictureFile.toString()
            }
            PictureType.ALBUM -> {
                val path = uri.toString()
            }
        }
        listener.onPhoto(photo)
        finish()
    }
}