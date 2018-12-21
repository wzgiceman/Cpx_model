package com.base.project.base.extension

import android.app.Activity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment

/**
 * Description:
 * Fragment扩展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

@JvmOverloads
fun Fragment.jumpActivity(cls: Class<*>, bundle: Bundle = Bundle()) {
    if (isValidActivity()) {
        activity.jumpActivity(cls, bundle)
    }
}

@JvmOverloads
fun Fragment.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    if (isValidActivity()) {
        activity.jumpActivityFinish(cls, bundle)
    }
}

fun Fragment.isValidActivity(): Boolean {
    return context != null && (context as Activity).isValidActivity()
}


@JvmOverloads
fun Fragment.report(@StringRes resId: Int, value: Bundle? = null) {
    context.report(resId, value)
}