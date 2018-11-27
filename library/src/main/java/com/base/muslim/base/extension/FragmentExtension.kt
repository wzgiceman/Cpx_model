package com.base.muslim.base.extension

import android.app.Activity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity

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

fun Fragment.getRxActivity(): RxAppCompatActivity? {
    if (context is RxAppCompatActivity) {
        return context as RxAppCompatActivity
    }
    return null
}

@JvmOverloads
fun Fragment.report(@StringRes resId: Int, value: Bundle? = null) {
    context.report(resId, value)
}
