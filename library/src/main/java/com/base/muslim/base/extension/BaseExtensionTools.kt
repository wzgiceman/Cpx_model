package com.base.muslim.base.extension

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.base.library.BuildConfig
import com.base.library.utils.DataReportUtils
import com.base.library.utils.utilcode.util.ToastUtils

/**
 * Description:
 * 扩展方法类
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
    if(content is String){
        ToastUtils.showShort(content)
        return
    }
    if(BuildConfig.DEBUG){
        ToastUtils.showShort(content?.toString())
    }
}

/**
 * 跳转到指定的Activity
 *
 * @param cls 指定的activity
 * @param bundle 携带的参数，可选
 */
@JvmOverloads
fun Context.jumpActivity(cls: Class<*>, bundle: Bundle = Bundle()) {
    val intent = Intent(this, cls)
    intent.putExtras(bundle)
    startActivity(intent)
}

@JvmOverloads
fun Activity.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    jumpActivity(cls, bundle)
    finish()
}

@JvmOverloads
fun Fragment.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    activity.jumpActivityFinish(cls, bundle)
}

/**
 * 埋点
 */
@JvmOverloads
fun report(key: String, value: Bundle? = null) {
    DataReportUtils.getInstance().report(key, value)
}


