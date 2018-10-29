package com.base.library.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Toast 工具类
 * @author xuechao
 * @date 2018/10/17 下午4:34
 * @copyright cpx
 */

public class AbToastUtil {

    /**
     * 显示toast信息
     * @param context
     * @param content
     */
    public static void showToast(Context context,String content){
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示toast信息
     * @param context
     * @param content
     */
    public static void showToast(Context context,@StringRes int content){
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }
}
