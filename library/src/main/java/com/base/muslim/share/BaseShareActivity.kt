package com.base.muslim.share

import android.content.Intent
import com.base.muslim.base.activity.BaseActivity

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
abstract class BaseShareActivity : BaseActivity(), OnShareListener {
    private val shareManager by lazy { ShareManager(this, this) }

    fun shareLink(type: String, link: String) {
        shareManager.shareLink(type, link)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        shareManager.handleActivityResult(requestCode, resultCode, data)
    }
}