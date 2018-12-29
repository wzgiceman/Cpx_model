package com.base.project.base.extension

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.base.library.BuildConfig
import com.base.library.rxbinding.view.RxView
import com.base.library.utils.DataReportUtils
import com.base.library.utils.utilcode.util.ToastUtils
import java.util.concurrent.TimeUnit

/**
 * Description:
 * 公共拓展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

/**
 * 显示toast提示信息
 *
 * @param content string或者resourceId
 */
fun showToast(content: Any?) {
    if (content is Int) {
        ToastUtils.showShort(content)
        return
    }
    if (content is String) {
        ToastUtils.showShort(content)
        return
    }
    if (BuildConfig.DEBUG) {
        ToastUtils.showShort(content?.toString())
    }
}

/**
 * 埋点
 */
@JvmOverloads
fun report(key: String, value: Bundle? = null) {
    DataReportUtils.getInstance().report(key, value)
}


/**
 * 防止多次点击
 * 默认一秒内重复点击无效
 */
@SuppressLint("CheckResult")
fun View.setOnRxClickListener(listener: (View) -> Unit) {
    RxView.clicks(this)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe { listener(this) }
}