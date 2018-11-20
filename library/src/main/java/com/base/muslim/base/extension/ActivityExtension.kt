package com.base.muslim.base.extension

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import com.base.library.R

/**
 * Description:
 * Activity扩展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

@JvmOverloads
fun Activity.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    jumpActivity(cls, bundle)
    finish()
}

/**
 * 判断Activity是否是合法
 */
fun Activity.isValidActivity(): Boolean {
    return !isDestroyed && !isFinishing
}

/**
 * Activity扩展属性loadingDialog
 */
val Activity.loadingDialog: ProgressDialog
    get() {
        return ProgressDialog(this)
    }

/**
 * 显示统一的加载框
 *
 * @param cancel 是否可以取消
 * @param title  显示的标题
 */
fun Activity.showLoading(cancel: Boolean, title: String) {
    if (!isValidActivity() || loadingDialog.isShowing) return
    val message = if (TextUtils.isEmpty(title)) getString(R.string.Loading) else title
    loadingDialog.setMessage(message)
    loadingDialog.setCancelable(cancel)
    loadingDialog.show()
}


/**
 * 关闭加载框
 */
fun Activity.closeLoading() {
    if (!isValidActivity()) return
    if (loadingDialog.isShowing) {
        loadingDialog.dismiss()
    }
}





