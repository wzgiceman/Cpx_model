package com.base.project.base.extension

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import com.base.library.utils.utilcode.util.KeyboardUtils

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
 * 点击空白区域隐藏软键盘
 */
fun Activity.checkKeyboard(ev: MotionEvent) {
    if (ev.action == MotionEvent.ACTION_DOWN) {
        val v = currentFocus
        if (isShouldHideKeyboard(v, ev)) {
            KeyboardUtils.hideSoftInput(this)
        }
    }
}


/**
 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，当用户点击EditText时不隐藏
 */
fun Activity.isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
    if (v != null && v is EditText) {
        val l = intArrayOf(0, 0)
        v.getLocationInWindow(l)
        val left = l[0]
        val top = l[1]
        val bottom = top + v.height
        val right = left + v.width
        return !(event.x > left && event.x < right
                && event.y > top && event.y < bottom)
    }
    return false
}





