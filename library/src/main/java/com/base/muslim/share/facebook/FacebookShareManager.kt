package com.base.muslim.share.facebook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.share.common.listener.OnShareListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.*
import com.facebook.share.widget.ShareDialog


/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class FacebookShareManager(val activity: Activity, val onShareListener: OnShareListener) : FacebookCallback<Sharer.Result> {

    val callbackManager by lazy { CallbackManager.Factory.create() }
    val shareDialog by lazy { ShareDialog(activity) }

    fun shareLink(link: String, tag: String, quote: String) {
        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .setShareHashtag(ShareHashtag.Builder()
                        .setHashtag(tag)
                        .build())
                .setQuote(quote)
                .build()
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    fun shareImage(image: Bitmap, tag: String) {
        val content = SharePhotoContent.Builder()
                .addPhoto(buildSharePhoto(image))
                .setShareHashtag(ShareHashtag.Builder()
                        .setHashtag(tag)
                        .build())
                .build()
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    fun buildSharePhoto(image: Bitmap): SharePhoto? {
        return SharePhoto.Builder()
                .setBitmap(image)
                .build()
    }

    fun shareVideo(videoUri: Uri, tag: String) {
        val content = ShareVideoContent.Builder()
                .setVideo(buildShareVideo(videoUri))
                .setShareHashtag(ShareHashtag.Builder()
                        .setHashtag(tag)
                        .build())
                .build()
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    fun buildShareVideo(videoUri: Uri): ShareVideo? {
        return ShareVideo.Builder()
                .setLocalUrl(videoUri)
                .build()
    }

    /**
     * 分享多媒体，每次可以分享最多包含 6 个照片和视频元素的内容
     */
    fun shareMedia(imageList: List<Bitmap>, videoUriList: List<Uri>, tag: String) {
        val shareContentBuilder = ShareMediaContent.Builder()
        for (image in imageList) {
            shareContentBuilder.addMedium(buildSharePhoto(image))
        }
        for (videoUri in videoUriList) {
            shareContentBuilder.addMedium(buildShareVideo(videoUri))
        }
        shareContentBuilder.setShareHashtag(ShareHashtag.Builder()
                .setHashtag(tag)
                .build())
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(shareContentBuilder.build(), ShareDialog.Mode.AUTOMATIC)
    }


    override fun onSuccess(result: Sharer.Result?) {
        onShareListener.onShareSuccess(FACEBOOK)
    }

    override fun onCancel() {
        onShareListener.onShareFail(FACEBOOK, "Facebook share cancel")
    }

    override fun onError(error: FacebookException?) {
        onShareListener.onShareFail(FACEBOOK, "Facebook share fail:$error")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

}