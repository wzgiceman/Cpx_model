/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.library.utils;

import android.content.Context;
import android.util.Log;

import com.base.library.rxRetrofit.RxRetrofitApp;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbLogUtil.java
 * 描述：日志工具类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2014-06-26 下午11:52:13
 */
public class AbLogUtil {

    /**
     * debug开关.
     */
    public static boolean D = RxRetrofitApp.isDebug();

    /**
     * info开关.
     */
    public static boolean I = RxRetrofitApp.isDebug();

    /**
     * error开关.
     */
    public static boolean E = RxRetrofitApp.isDebug();

    /**
     * 起始执行时间.
     */
    public static long startLogTimeInMillis = 0;

    /**
     * debug日志
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (D) Log.d(tag, message);
    }

    public static void d(String message) {
        if (D) Log.d("tag", message);
    }

    /**
     * debug日志
     *
     * @param context
     * @param message
     */
    public static void d(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        d(tag, message);
    }

    /**
     * debug日志
     *
     * @param clazz
     * @param message
     */
    public static void d(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        d(tag, message);
    }

    /**
     * info日志
     *
     * @param context
     * @param message
     */
    public static void i(Context context, String message) {
        if (I) {
            String tag = context.getClass().getSimpleName();
            i(tag, message);
        }
    }

    /**
     * info日志
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (I) {
            Log.i(tag, message);
        }
    }

    /**
     * info日志
     *
     * @param clazz
     * @param message
     */
    public static void i(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        i(tag, message);
    }


    /**
     * error日志
     *
     * @param message
     */
    public static void e(String message) {
        if (E) {
            Log.e("tag", message);
        }
    }

    /**
     * error日志
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (E) {
            Log.e(tag, message);
        }
    }

    /**
     * error日志
     *
     * @param context
     * @param message
     */
    public static void e(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        e(tag, message);
    }

    /**
     * error日志
     *
     * @param clazz
     * @param message
     */
    public static void e(Class<?> clazz, String message) {
        String tag = clazz.getSimpleName();
        e(tag, message);
    }


    /**
     * debug日志的开关
     *
     * @param d
     */
    public static void debug(boolean d) {
        D = d;
    }

    /**
     * info日志的开关
     *
     * @param i
     */
    public static void info(boolean i) {
        I = i;
    }

    /**
     * error日志的开关
     *
     * @param e
     */
    public static void error(boolean e) {
        E = e;
    }

    /**
     * 设置日志的开关
     *
     * @param e
     */
    public static void setVerbose(boolean d, boolean i, boolean e) {
        D = d;
        I = i;
        E = e;
    }

    public static void logSwitch(Boolean bug) {
        if (bug) {
            openAll();
        } else {
            closeAll();
        }
    }

    /**
     * 打开所有日志，默认全打开
     */
    public static void openAll() {
        D = true;
        I = true;
        E = true;
    }

    /**
     * 关闭所有日志
     */
    public static void closeAll() {
        D = false;
        I = false;
        E = false;
    }
}
