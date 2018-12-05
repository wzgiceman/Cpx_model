package com.prog.zhigangwei.cpx_model.share

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
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
class ShareActivity : BaseShareActivity() {
    private var uri: Uri = Uri.EMPTY

    companion object {
        const val REQUEST_CODE_CHOOSE_LOCAL_VIDEO = 1009
    }

    override fun layoutId() = R.layout.activity_share

    override fun initView() {
        btn_share_text_by_facebook.setOnClickListener {
            shareText(FACEBOOK,
                    "The model is as steady as an old dog!")
        }

        btn_share_link_by_facebook.setOnClickListener {
            shareLink(FACEBOOK,
                    "https://github.com/wzgiceman/Cpx_model",
                    "#CPXModel",
                    "The model is as steady as an old dog!")
        }

        btn_share_image_by_facebook.setOnClickListener {
            shareImage(FACEBOOK,
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    "#CPXModel")
        }

        btn_choose_local_video.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "video/*"
            }, REQUEST_CODE_CHOOSE_LOCAL_VIDEO)
        }
        btn_share_video_by_facebook.setOnClickListener {
            if (!checkUri()) return@setOnClickListener
            shareVideo(FACEBOOK,
                    uri,
                    "#CPXModel")
        }

        btn_share_media_by_facebook.setOnClickListener {
            if (!checkUri()) return@setOnClickListener
            shareMedia(FACEBOOK,
                    listOf(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)),
                    listOf(uri, uri, uri),
                    "#CPXModel")
        }
        btn_share_text_by_twitter.setOnClickListener {
            shareText(TWITTER,
                    "The model is as steady as an old dog!")
        }

        btn_share_image_by_twitter.setOnClickListener {
            shareImage(TWITTER,
                    BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                    "#CPXModel")
        }
    }

    fun checkUri(): Boolean {
        if (uri == Uri.EMPTY) {
            tv_local_video_uri.text = "please click \"Choose local video\" button to select a local video first"
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE_LOCAL_VIDEO && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data
            tv_local_video_uri.text = "Local video uri: $uri"
        }
    }

    override fun initData() {
    }

    override fun onShareSuccess(type: String) {
        tv_share_status.append("$type 分享成功\n\n")
    }

    override fun onShareFail(type: String, cause: String) {
        tv_share_status.append("$type 分享失败\ncause:$cause\n\n")
    }
}
