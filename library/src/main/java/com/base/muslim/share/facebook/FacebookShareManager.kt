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
 * Facebook分享管理类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class FacebookShareManager(private val activity: Activity, private val onShareListener: OnShareListener) : FacebookCallback<Sharer.Result> {

    private val callbackManager by lazy { CallbackManager.Factory.create() }
    private val shareDialog by lazy { ShareDialog(activity) }

    /**
     * 分享文字
     * @param text 文字内容
     */
    fun shareText(text: String) {
        val content = ShareLinkContent.Builder()
                .setShareHashtag(ShareHashtag.Builder()
                        .setHashtag(text)
                        .build())
                .setQuote(text)
                .build()
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    /**
     * 分享链接
     * @param link 链接地址
     * @param tag 文字内容
     * @param quote 引文内容，和文字内容有不一样的样式
     */
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

    /**
     * 分享图片
     * @param image 图片Bitmap
     * @param tag 文字内容
     */
    fun shareImage(image: Bitmap, tag: String) {
        shareBitmapOrUri(image, tag)
    }

    /**
     * 分享图片
     * @param image 图片Uri
     * @param tag 文字内容
     */
    fun shareImage(image: Uri, tag: String) {
        shareBitmapOrUri(image, tag)
    }

    private fun shareBitmapOrUri(image: Any, tag: String) {
        val content = SharePhotoContent.Builder()
                .addPhoto(buildSharePhoto(image))
                .setShareHashtag(ShareHashtag.Builder()
                        .setHashtag(tag)
                        .build())
                .build()
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    private fun buildSharePhoto(image: Any): SharePhoto? {
        return when (image) {
            is Bitmap -> SharePhoto.Builder()
                    .setBitmap(image)
                    .build()
            is Uri -> SharePhoto.Builder()
                    .setImageUrl(image)
                    .build()
            else -> null
        }
    }

    /**
     * 分享本地视频，通过tag添加文字
     * @param videoUri 本地视频Uri
     * @param tag 文字内容
     */
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

    private fun buildShareVideo(videoUri: Uri): ShareVideo? {
        return ShareVideo.Builder()
                .setLocalUrl(videoUri)
                .build()
    }

    /**
     * 分享多媒体，图片+视频总数最多6个
     * @param imageList 图片Bitmap列表
     * @param videoUriList 本地视频Uri列表
     * @param tag 文字内容
     *
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