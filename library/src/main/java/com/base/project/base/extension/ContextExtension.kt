package com.base.project.base.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes

/**
 * Description:
 * Context扩展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

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
fun Context.report(@StringRes resId: Int, value: Bundle? = null) {
    report(getString(resId), value)
}