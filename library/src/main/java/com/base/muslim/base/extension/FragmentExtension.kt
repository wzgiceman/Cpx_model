package com.base.muslim.base.extension

import android.os.Bundle
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
fun Fragment.jumpActivityFinish(cls: Class<*>, bundle: Bundle = Bundle()) {
    activity.jumpActivityFinish(cls, bundle)
}

fun Fragment.isValidActivity(): Boolean {
    return activity != null && activity.isValidActivity()
}
