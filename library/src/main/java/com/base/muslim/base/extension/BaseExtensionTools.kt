package com.base.muslim.base.extension

import android.content.Context
import com.base.library.utils.AbToastUtil

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
 * @param content
 */
fun Context.showToast(content: String) {
    AbToastUtil.showToast(this, content)
}

/**
 * 显示toast提示信息
 *
 * @param content
 */
fun Context.showToast(content: Int) {
    AbToastUtil.showToast(this, content)
}

