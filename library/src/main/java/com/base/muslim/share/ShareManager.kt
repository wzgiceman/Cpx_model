package com.base.muslim.share

import android.app.Activity
import android.content.Intent
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
    fun shareLink(type: String, link: String) {
        when (type) {
            FACEBOOK -> facebookShareManager.shareLinkByFacebook(link)
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookShareManager.handleActivityResult(requestCode, resultCode, data)
    }
}