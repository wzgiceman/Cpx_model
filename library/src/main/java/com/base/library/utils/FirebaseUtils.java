package com.base.library.utils;

import android.os.Bundle;

import com.base.library.retrofit_rx.RxRetrofitApp;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * firebase埋点统计 和facebook 事件统计
 * firebase utils
 * @auth zhigang wei
 */
public class FirebaseUtils {

    private static volatile FirebaseUtils intance;
    private static FirebaseAnalytics mFirebaseAnalytics;

    /**
     * Facebook 统计
     */
    private static AppEventsLogger mLogger;

    public static FirebaseUtils getInstance() {
        if (intance == null) {
            intance = new FirebaseUtils();
        }
        return intance;
    }

    private FirebaseUtils() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(RxRetrofitApp.getApplication());
        mLogger = AppEventsLogger.newLogger(RxRetrofitApp.getApplication());
    }

    /**
     * 埋点
     *
     * @param key
     * @param value
     */
    public void report(String key, Bundle value) {
        if (value == null) {
            value = new Bundle();
        }
        value.putInt(key, 1);
        mFirebaseAnalytics.logEvent(key, value);
        mLogger.logEvent(key,value);
    }

    /**
     * 埋点
     *
     * @param key
     */
    public void report(String key) {
        report(key, null);
    }

}
