package com.base.muslim.share

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK


/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareManager(activity: Activity, onShareListener: OnShareListener) {
    private val facebookShareManager by lazy { FacebookShareManager(activity, onShareListener) }
    fun shareLink(type: String, link: String, tag: String, quote: String) {
        when (type) {
            FACEBOOK -> facebookShareManager.shareLink(link, tag, quote)
        }
    }

    fun shareImage(type: String, image: Bitmap, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareImage(image, tag)
        }
    }

    fun shareVideo(type: String, videoUri: Uri, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareVideo(videoUri, tag)
        }
    }

    fun shareMedia(type: String, imageList: List<Bitmap>, videoUriList: List<Uri>, tag: String = "") {
        when (type) {
            FACEBOOK -> facebookShareManager.shareMedia(imageList, videoUriList, tag)
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager.handleActivityResult(requestCode, resultCode, data)
    }
}