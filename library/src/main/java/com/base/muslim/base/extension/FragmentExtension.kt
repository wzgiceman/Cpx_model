package com.base.muslim.base.extension

import android.os.Bundle
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
fun Fragment.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    activity.jumpActivityFinish(cls, bundle)
}

fun Fragment.isValidActivity(): Boolean {
    return activity != null && activity.isValidActivity()
}

fun Fragment.getRxActivity(): RxAppCompatActivity? {
    if (context is RxAppCompatActivity) {
        return context as RxAppCompatActivity
    }
    return null
}

/**
 * 显示统一的加载框
 *
 * @param cancel 是否可以取消
 * @param title  显示的标题
 */
fun Fragment.showLoading(cancel: Boolean, title: String) {
    if (isValidActivity()) {
        activity.showLoading(cancel, title)
    }
}

/**
 * 关闭加载框
 */
fun Fragment.closeLoading() {
    if (isValidActivity()) {
        activity.closeLoading()
    }
}
