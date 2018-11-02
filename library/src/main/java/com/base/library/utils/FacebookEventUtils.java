package com.base.library.utils;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.library.retrofit_rx.RxRetrofitApp;
import com.facebook.appevents.AppEventsLogger;

/**
 * facebook 统计事件的工具类
 *
 * 统计事件和FirebaseUtils和并了
 * @see FirebaseUtils
 * @author xuechao
 * @date 2018/11/2 上午9:26
 * @copyright cpx
 */
@Deprecated
public class FacebookEventUtils {

    private static FacebookEventUtils mInstance;
    private static AppEventsLogger mlogger;

    private FacebookEventUtils() {
        mlogger = AppEventsLogger.newLogger(RxRetrofitApp.getApplication());
    }

    /**
     * 获取单例
     * @return
     */
    public static FacebookEventUtils getInstance(){
        if(null == mInstance){
            mInstance = new FacebookEventUtils();
        }
        return mInstance;
    }

    /**
     * 应用启动统计事件
     */
    public void activateApp(){
        AppEventsLogger.activateApp(RxRetrofitApp.getApplication());
    }

    /**
     * 应用启动统计事件
     * @param app
     */
    public void activateApp(Application app){
        AppEventsLogger.activateApp(app);
    }

    /**
     * facebook 统计事件
     * @param key
     */
    public void logEvent(String key){
        logEvent(key,null);
    }


    /**
     * facebook 统计事件
     * @param key
     * @param value
     */
    public void logEvent(String key,@Nullable Bundle value){
        if(null == value){
            value = new Bundle();
        }
        mlogger.logEvent(key,value);
    }


}
