package com.base.muslim.share

import android.net.Uri
import com.facebook.share.model.ShareLinkContent



/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class ShareUtils{
    fun shareByFacebook(){
        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build()
    }
}