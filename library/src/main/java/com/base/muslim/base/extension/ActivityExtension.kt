package com.base.muslim.base.extension

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Description:
 * Activity扩展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

/**
 * Activity扩展属性loadingDialog
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
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(v?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}

/**
 * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，当用户点击EditText时不隐藏
 */
fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
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





