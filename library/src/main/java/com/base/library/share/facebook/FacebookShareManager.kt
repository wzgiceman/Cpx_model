package com.base.library.share.facebook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import com.base.library.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.library.share.common.listener.OnShareListener
import com.base.library.share.common.util.ShareUtils
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
        shareLink(tag = text)
    }

    /**
     * 分享链接
     * @param link 链接地址
     * @param tag 文字内容
     * @param quote 引文内容，和文字内容有不一样的样式
     */
    fun shareLink(link: String = "", tag: String = "", quote: String = "") {
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
     * 分享本地图片
     * @param image 图片Bitmap或者图片Uri
     * @param tag 文字内容
     */
    fun shareImage(image: Any, tag: String) {
        shareMedia(imageList = listOf(image), tag = tag)
    }

    /**
     * 分享本地视频，通过tag添加文字
     * @param videoUri 本地视频Uri
     * @param tag 文字内容
     */
    fun shareVideo(videoUri: Uri, tag: String) {
        shareMedia(videoUriList = listOf(videoUri), tag = tag)
    }

    /**
     * 分享多媒体，图片+视频总数最多6个
     * @param imageList 图片Bitmap列表
     * @param videoUriList 本地视频Uri列表
     * @param tag 文字内容
     */
    fun shareMedia(imageList: List<Any> = ArrayList(), videoUriList: List<Uri> = ArrayList(), tag: String = "") {
        val shareContentBuilder = ShareMediaContent.Builder()
        for (image in imageList) {
            shareContentBuilder.addMedium(buildSharePhoto(image))
        }
        for (video in videoUriList) {
            shareContentBuilder.addMedium(buildShareVideo(video))
        }
        /**必须添加一个Medium*/
        if (imageList.isEmpty() && videoUriList.isEmpty()) {
            shareContentBuilder.addMedium(buildShareVideo(Uri.EMPTY))
        }
        shareContentBuilder.setShareHashtag(ShareHashtag.Builder()
                .setHashtag(tag)
                .build())
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(shareContentBuilder.build(), ShareDialog.Mode.AUTOMATIC)
    }

    private fun buildSharePhoto(image: Any): SharePhoto? {
        val imageUri = when (image) {
            is Bitmap -> ShareUtils.bitmap2Uri(image)
            is Uri -> image
            else -> Uri.EMPTY
        }
        return SharePhoto.Builder()
                .setImageUrl(imageUri)
                .build()
    }

    private fun buildShareVideo(videoUri: Uri): ShareVideo? {
        return ShareVideo.Builder()
                .setLocalUrl(videoUri)
                .build()
    }

    override fun onSuccess(result: Sharer.Result?) {
        ShareUtils.clearShareTempPictures()
        onShareListener.onShareSuccess(FACEBOOK)
    }

    override fun onCancel() {
        ShareUtils.clearShareTempPictures()
        onShareListener.onShareFail(FACEBOOK, "Facebook share cancel")
    }

    override fun onError(error: FacebookException?) {
        ShareUtils.clearShareTempPictures()
        onShareListener.onShareFail(FACEBOOK, "Facebook share fail:$error")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}