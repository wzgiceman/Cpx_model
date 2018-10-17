package watermark.wyf.com.watermarkdemo.watermark

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils

/**
 * 给图片添加水印
 *create time 2018/9/12
 * @author wyf
 */
class Watermark private constructor() {
    private var width = 0f
    private var height = 0f
    private var gravity = RIGHT_BOTTOM //水印的显示位置，默认右下角
    private val paint by lazy { Paint() }
    private var textWith = 0f //文字水印的宽
    private var watermarkWidth = 0f //图片水印的宽
    private var watermarkHeight = 0f //图片水印的高
    private var alpha = 255  //水印透明度
    private var isRecycle = false

    companion object {
        /*------------水印显示在图片上的位置------------------*/
        const val LEFT_TOP = "left_top" //左上角
        const val LEFT_CENTER = "left_center" //左边居中
        const val LEFT_BOTTOM = "left_bottom"  //左下角
        const val TOP_CENTER = "top_center"  //顶部居中
        const val CENTER = "center"  //完全居中
        const val BOTTOM_CENTER = "bottom_center" //底部居中
        const val RIGHT_TOP = "right_top" //右上角
        const val RIGHT_CENTER = "right_center"  //右边居中
        const val RIGHT_BOTTOM = "right_bottom"  //右下角

        fun getInstance() = WatermarkHolder.INSTANCE
    }

    private object WatermarkHolder {
        val INSTANCE = Watermark()
    }

    /**
     * 给图片添加文字水印
     *
     * @param targetBitmap 需要添加水印的图片的bitmap
     * @param text  水印文字
     * @param color 水印文字的颜色
     * @param gravity 水印显示的位置
     * @param textSize 水印文字的大小
     * @param alpha 水印透明度值（0 - 255），值越小越透明
     */
    fun createTextWatermark(targetBitmap: Bitmap, text: String, color: Int, gravity: String, textSize: Float, alpha: Int): Bitmap? {
        this.gravity = gravity
        this.alpha = alpha
        this.isRecycle = false
        return getWatermarkBitmap(targetBitmap, null, text, color, textSize)
    }

    /**
     * 给图片添加图片水印
     *
     * @param targetBitmap 需要添加水印的图片的bitmap
     * @param watermarkBitmap  作为水印的图片的bitmap
     * @param gravity 水印显示的位置
     * @param alpha 水印透明度值（0 - 255） ，值越小越透明
     */
    fun createPictureWatermark(targetBitmap: Bitmap, watermarkBitmap: Bitmap, gravity: String, alpha: Int): Bitmap? {
        this.gravity = gravity
        this.alpha = alpha
        this.isRecycle = false
        return getWatermarkBitmap(targetBitmap, watermarkBitmap, "", 0, 0f)
    }

    /**
     * 给图片添加图片水印
     *
     * @param context
     * @param targetBitmap 需要添加水印的图片的bitmap
     * @param resourceId  作为水印的图片资源id
     * @param gravity 水印显示的位置
     * @param alpha 水印透明度值（0 - 255） ，值越小越透明
     */
    fun createPictureWatermark(context: Context, targetBitmap: Bitmap, resourceId: Int, gravity: String, alpha: Int): Bitmap? {
        this.gravity = gravity
        this.alpha = alpha
        this.isRecycle = true
        return getWatermarkBitmap(targetBitmap, BitmapFactory.decodeResource(context.resources, resourceId), "", 0, 0f)
    }

    /**
     * 获取添加了水印后的bitmap
     */
    private fun getWatermarkBitmap(targetBitmap: Bitmap, watermark: Bitmap?, text: String, color: Int, textSize: Float): Bitmap? {
        getWatermarkgravity(targetBitmap)
        val watermarkBitmap = Bitmap.createBitmap(targetBitmap.width, targetBitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(watermarkBitmap)
        canvas.drawBitmap(targetBitmap, 0f, 0f, null) // 绘制原图
        if (!TextUtils.isEmpty(text)) {
            drawTextWatermark(canvas, text, color, textSize)  //绘制文字水印
        }
        if (watermark != null) {
            drawPictureWatermark(canvas, watermark) //绘制图片水印
        }
        if (isRecycle) {
            watermark!!.recycle()
        }
        return watermarkBitmap
    }

    /**
     * 绘制文字水印
     */
    private fun drawTextWatermark(canvas: Canvas, text: String, color: Int, textSize: Float) {
        paint.color = color
        paint.textSize = textSize
        paint.isAntiAlias = true
        textWith = paint.measureText(text)
        paint.alpha = alpha
        when (gravity) {  //文字水印显示的详细位置
            TOP_CENTER, CENTER, BOTTOM_CENTER -> {
                width -= textWith / 2
            }
            RIGHT_TOP, RIGHT_CENTER, RIGHT_BOTTOM -> {
                width = width - textWith - 15
            }
        }
        canvas.drawText(text, width, height, paint)    //绘制文字水印
    }

    /**
     * 绘制图片水印
     */
    private fun drawPictureWatermark(canvas: Canvas, watermark: Bitmap) {
        watermarkWidth = watermark.width.toFloat()
        watermarkHeight = watermark.height.toFloat()
        getPictureWatermarkGravity()
        paint.alpha = alpha
        canvas.drawBitmap(watermark, width, height, paint) //绘制图片水印
    }

    /**
     * 图片水印显示的详细位置
     */
    private fun getPictureWatermarkGravity() {
        when (gravity) {
            TOP_CENTER -> {
                width -= watermarkWidth / 2
                height -= 20
            }
            CENTER -> {
                width -= watermarkWidth / 2
                height -= watermarkHeight / 2
            }
            BOTTOM_CENTER -> {
                width -= watermarkWidth / 2
                height -= watermarkHeight
            }
            LEFT_CENTER -> {
                height -= watermarkHeight / 2
            }
            LEFT_BOTTOM -> {
                height -= watermarkHeight
            }
            RIGHT_TOP -> {
                width = width - watermarkWidth - 15
                height -= 20
            }
            RIGHT_CENTER -> {
                width = width - watermarkWidth - 15
                height -= watermarkHeight / 2
            }
            RIGHT_BOTTOM -> {
                width = width - watermarkWidth - 15
                height -= watermarkHeight
            }
        }
    }

    /**
     * 获取水印显示在图片上的初始位置
     */
    private fun getWatermarkgravity(targetBitmap: Bitmap) {
        when (gravity) {
            LEFT_TOP -> { //水印显示在左上角
                width = (targetBitmap.width / 55).toFloat()
                height = (targetBitmap.height / 18).toFloat()
            }
            LEFT_CENTER -> { //水印显示在左边居中
                width = (targetBitmap.width / 55).toFloat()
                height = (targetBitmap.height / 2).toFloat()
            }
            LEFT_BOTTOM -> {  //水印显示在左下角
                width = (targetBitmap.width / 55).toFloat()
                height = (targetBitmap.height - 15).toFloat()
            }
            TOP_CENTER -> {  //顶部居中
                width = (targetBitmap.width / 2).toFloat()
                height = (targetBitmap.width / 20).toFloat()
            }
            CENTER -> {  //完全居中
                width = (targetBitmap.width / 2).toFloat()
                height = (targetBitmap.height / 2).toFloat()
            }
            BOTTOM_CENTER -> {  //底部居中
                width = (targetBitmap.width / 2).toFloat()
                height = (targetBitmap.height - 15).toFloat()
            }
            RIGHT_TOP -> {  //右上角
                width = targetBitmap.width.toFloat()
                height = (targetBitmap.width / 20).toFloat()
            }
            RIGHT_CENTER -> {  //右边居中
                width = targetBitmap.width.toFloat()
                height = (targetBitmap.height / 2).toFloat()
            }
            RIGHT_BOTTOM -> {   //右下角
                width = targetBitmap.width.toFloat()
                height = (targetBitmap.height - 15).toFloat()
            }
        }
    }

}