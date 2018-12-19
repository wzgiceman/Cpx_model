package com.base.project.base.extension

import android.app.Dialog
import android.os.Bundle
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

fun Dialog.getString(@StringRes resId: Int,vararg formatArgs:Any):String{
    return context.getString(resId,formatArgs)
}


fun Dialog.jumpActivity(cls: Class<*>, bundle: Bundle = Bundle()){
    context.jumpActivity(cls, bundle)
}