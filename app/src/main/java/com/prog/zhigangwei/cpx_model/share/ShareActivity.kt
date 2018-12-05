package com.prog.zhigangwei.cpx_model.share

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import com.base.library.utils.utilcode.util.TimeUtils
import com.base.muslim.share.base.BaseShareActivity
import com.base.muslim.share.common.constants.ShareConstants.Companion.FACEBOOK
import com.base.muslim.share.common.constants.ShareConstants.Companion.TWITTER
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_share.*

/**
 * Description:
 * 分享页面Demo
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/5
 */
@SuppressLint("SetTextI18n")
class ShareActivity : BaseShareActivity() {
    private var localVideoUri: Uri = Uri.EMPTY
    private var localImageUri: Uri = Uri.EMPTY

    companion object {
        const val REQUEST_CODE_CHOOSE_LOCAL_IMAGE = 1008
        const val REQUEST_CODE_CHOOSE_LOCAL_VIDEO = 1009
    }

    override fun layoutId() = R.layout.activity_share

    override fun initView() {
        /**facebook分享文字*/
        btn_share_text_by_facebook.setOnClickListener {
            shareText(FACEBOOK,
                    "The model is as steady as an old dog!")
        }

        /**facebook分享链接*/
        btn_share_link_by_facebook.setOnClickListener {
            shareLink(FACEBOOK,
                    "https://github.com/wzgiceman/Cpx_model",
                    "#CPXModel",
                    "The model is as steady as an old dog!")
        }

        /**facebook分享bitmap图片*/
        btn_share_bitmap_by_facebook.setOnClickListener {
            shareImage(FACEBOOK,
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    "#CPXModel")
        }

        /**选择本地图片*/
        btn_choose_local_image.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }, REQUEST_CODE_CHOOSE_LOCAL_IMAGE)
        }

        /**facebook分享本地图片*/
        btn_share_local_image_by_facebook.setOnClickListener {
            if (!checkLocalImageUri()) return@setOnClickListener
            shareImage(FACEBOOK,
                    localImageUri,
                    "#CPXModel")
        }

        /**选择本地视频*/
        btn_choose_local_video.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }, REQUEST_CODE_CHOOSE_LOCAL_VIDEO)
        }

        /**facebook分享本地视频*/
        btn_share_video_by_facebook.setOnClickListener {
            if (!checkLocalVideoUri()) return@setOnClickListener
            shareVideo(FACEBOOK,
                    localVideoUri,
                    "#CPXModel")
        }

        /**facebook分享多张图片、视频*/
        btn_share_media_by_facebook.setOnClickListener {
            if (!checkLocalVideoUri()) return@setOnClickListener
            shareMedia(FACEBOOK,
                    listOf(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)),
                    listOf(localVideoUri, localVideoUri, localVideoUri),
                    "#CPXModel")
        }

        /**twitter分享文字*/
        btn_share_text_by_twitter.setOnClickListener {
            shareText(TWITTER,
                    "The model is as steady as an old dog!")
        }

        /**twitter分享bitmap图片*/
        btn_share_bitmap_by_twitter.setOnClickListener {
            shareImage(TWITTER,
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    "#CPXModel")
        }

        /**twitter分享本地图片*/
        btn_share_local_image_by_twitter.setOnClickListener {
            if (!checkLocalImageUri()) return@setOnClickListener
            shareImage(TWITTER,
                    localImageUri,
                    "#CPXModel")
        }
    }

    private fun checkLocalVideoUri(): Boolean {
        if (localVideoUri == Uri.EMPTY) {
            tv_local_video_uri.text = "please click \"Choose Local Video\" button to select a local video first"
            return false
        }
        return true
    }

    private fun checkLocalImageUri(): Boolean {
        if (localImageUri == Uri.EMPTY) {
            tv_local_image_uri.text = "please click \"Choose Local Image\" button to select a local image first"
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE_LOCAL_VIDEO && resultCode == Activity.RESULT_OK && data != null) {
            localVideoUri = data.data
            tv_local_video_uri.text = "Local video uri: $localVideoUri"
        }
        if (requestCode == REQUEST_CODE_CHOOSE_LOCAL_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            localImageUri = data.data
            tv_local_image_uri.text = "Local image uri: $localImageUri"
        }
    }

    override fun initData() {
    }

    override fun onShareSuccess(type: String) {
        tv_share_status.append("${TimeUtils.millis2String(System.currentTimeMillis())}: $type 分享成功\n\n")
    }

    override fun onShareFail(type: String, cause: String) {
        tv_share_status.append("${TimeUtils.millis2String(System.currentTimeMillis())}: $type 分享失败\ncause:$cause\n\n")
    }
}
