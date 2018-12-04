package com.prog.zhigangwei.cpx_model.share

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import com.base.muslim.share.BaseShareActivity
import com.base.muslim.share.ShareConstants.Companion.FACEBOOK
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : BaseShareActivity() {
    private var uri: Uri = Uri.EMPTY

    companion object {
        const val REQUEST_CODE_CHOOSE_LOCAL_VIDEO = 1009
    }

    override fun layoutId() = R.layout.activity_share

    override fun initView() {
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
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_LOCAL_VIDEO)
        }
        btn_share_video_by_facebook.setOnClickListener {
            if (uri == Uri.EMPTY) {
                tv_local_video_uri.text = "please click \"Choose local video\" button to select a local video first"
                return@setOnClickListener
            }
            shareVideo(FACEBOOK,
                    uri,
                    "#CPXModel")
        }

        btn_share_media_by_facebook.setOnClickListener {
            if (uri == Uri.EMPTY) {
                tv_local_video_uri.text = "please click \"Choose local video\" button to select a local video first"
                return@setOnClickListener
            }
            shareMedia(FACEBOOK,
                    listOf(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
                            BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)),
                    listOf(uri, uri, uri),
                    "#CPXModel")
        }
//        btn_share_by_google.setOnClickListener {
//            val shareIntent = PlusShare.Builder(this)
//                    .setType("text/plain")      //  不变
//                    .setText("Welcome to the Google+ platform.")   // 自定义内容
//                    .setContentUrl(Uri.parse("https://developers.google.com/+/"))   // 自定义链接地址
//                    .getIntent();
//            startActivityForResult(shareIntent, 0);
//        }

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
