package com.base.muslim.base.extension

import android.app.Dialog
import android.support.annotation.StringRes

/**
 * Description:
 * Dialog扩展方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */

fun Dialog.getString(@StringRes resId: Int): String {
    return context.getString(resId)
}