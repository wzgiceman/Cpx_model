package com.base.muslim.base.extension

import android.app.Activity
import android.os.Bundle

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





