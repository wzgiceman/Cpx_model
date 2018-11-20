package com.base.muslim.base.extension

import android.os.Bundle
import com.base.library.BuildConfig
import com.base.library.utils.DataReportUtils
import com.base.library.utils.utilcode.util.ToastUtils

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