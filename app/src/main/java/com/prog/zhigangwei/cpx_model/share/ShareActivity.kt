package com.prog.zhigangwei.cpx_model.share

import com.base.muslim.share.BaseShareActivity
import com.base.muslim.share.ShareConstants.Companion.FACEBOOK
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : BaseShareActivity() {
    override fun layoutId() = R.layout.activity_share

    override fun initView() {
        btn_share_by_facebook.setOnClickListener { shareLink(FACEBOOK,"http://developers.facebook.com/android") }
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
