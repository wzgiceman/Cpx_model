package com.base.muslim.share

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class FacebookShareManager(val activity: Activity, val onShareListener: OnShareListener): FacebookCallback<Sharer.Result> {

    val callbackManager by lazy { CallbackManager.Factory.create() }
    val shareDialog by lazy { ShareDialog(activity) }

    fun shareLinkByFacebook(link:String) {
        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .build()
        // this part is optional
        shareDialog.registerCallback(callbackManager, this)
        shareDialog.show(content)
    }

    override fun onSuccess(result: Sharer.Result?) {
        onShareListener.onShareSuccess(FACEBOOK)
    }

    override fun onCancel() {
        onShareListener.onShareFail(FACEBOOK,"Facebook share cancel")
    }

    override fun onError(error: FacebookException?) {
        onShareListener.onShareFail(FACEBOOK,"Facebook share fail:$error")
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }
}