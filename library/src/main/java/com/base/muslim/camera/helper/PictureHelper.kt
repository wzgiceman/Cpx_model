package com.base.muslim.camera.helper

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File
import java.io.FileOutputStream


/**
 * Created by cpx on 2018/4/2.
 * 拍照或从相册选择图片
 */
object PictureHelper {
    const val REQUESTCODE_CAMERA = 0x01
    const val REQUESTCODE_ALBUM = 0x02
    const val REQUESTCODE_CROP = 0x03

    /**
     * @param activity    当前activity
     */
    fun takePicture(activity: Activity, file: File) {
        val imageUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(activity, activity.packageName + ".provider", file)
        } else {
            Uri.fromFile(file)
        }
        //调用系统相机
        val intentCamera = Intent()
        intentCamera.action = MediaStore.ACTION_IMAGE_CAPTURE
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        activity.startActivityForResult(intentCamera, REQUESTCODE_CAMERA)
    }

    /**
     * @param activity    当前activity
     */
    fun openAlbum(activity: Activity) {
//        val photoPickerIntent = Intent(Intent.ACTION_GET_CONTENT)
//        photoPickerIntent.type = "image/*"
//        activity.startActivityForResult(photoPickerIntent, REQUESTCODE_ALBUM)
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        activity.startActivityForResult(intent, REQUESTCODE_ALBUM)
    }

    /**
     * @param activity    当前activity
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     */
    fun cropImageUri(activity: Activity, orgUri: Uri, desUri: Uri, aspectX: Int, aspectY: Int, width: Int, height: Int) {
        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(orgUri, "image/*")
        //发送裁剪信号
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", aspectX)
        intent.putExtra("aspectY", aspectY)
        intent.putExtra("outputX", width)
        intent.putExtra("outputY", height)
        intent.putExtra("scale", true)
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri)
        //1-false用uri返回图片
        //2-true直接用bitmap返回图片（此种只适用于小图片，返回图片过大会报错）
        intent.putExtra("return-data", false)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        activity.startActivityForResult(intent, REQUESTCODE_CROP)
    }


    fun compressBitmap(filePath: String, file: File, reqWidth: Int, reqHeight: Int) {
        // 数值越高，图片像素越低

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        //采样率
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeFile(filePath, options)

        val baos = FileOutputStream(file)
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    }

    //计算图片的缩放值
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }
}