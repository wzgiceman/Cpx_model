package com.base.router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.base.library.utils.utilcode.util.LogUtils;
import com.base.router.empty.EmptyActivity;

/**
 * Describe:路由器
 * <p>
 * <p>
 * Created by zhigang wei
 * on 2018/4/18
 * <p>
 * <p>
 * Company :cpx
 */
public class ActivityRouter {


    private static void startActivity(Context context, Class clazz, Bundle bundle) {
        Intent intent = getIntent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    private static Intent getIntent(Context context, Class clazz) {
        return new Intent(context, clazz);
    }

    /**
     * 带参数得跳转
     * @param context
     * @param name
     * @param bundle
     */
    public static void startActivityForName(Context context, String name, Bundle bundle) {
        try {
            Class clazz = Class.forName(name);
            startActivity(context, clazz, bundle);
        } catch (ClassNotFoundException e) {
            startActivity(context, EmptyActivity.class,null);
            LogUtils.e("The class cannot be found");
        }
    }

    /**
     * 不带参数得跳转
     * @param context
     * @param name
     */
    public static void startActivityForName(Context context, String name) {
        try {
            Class clazz = Class.forName(name);
            startActivity(context, clazz, null);
        } catch (ClassNotFoundException e) {
            startActivity(context, EmptyActivity.class,null);
            LogUtils.e("The class cannot be found");
        }
    }
}
