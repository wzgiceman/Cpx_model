package com.base.muslim.base.fragment

import com.base.muslim.base.extension.getRxActivity
import com.base.muslim.base.extension.loadingDialog


/**
 * fragment基础工具类
 * Created by WZG on 2016/1/28.
 */
open class BaseToolFragment : BaseFragmentManagerFragment() {
    override fun onDestroy() {
        super.onDestroy()
        val rxActivity = getRxActivity() ?: return
        if (rxActivity.loadingDialog.isShowing) {
            rxActivity.loadingDialog.dismiss()
        }
    }
}
