package com.base.library.retrofit_rx;

import android.app.Application;

import com.base.library.R;
import com.base.library.utils.AbAppUtil;

/**
 * 全局app
 * Created by WZG on 2016/12/12.
 */

public class RxRetrofitApp {
    private static Application application;
    private static boolean debug;


    public static void init(Application app) {
        init(app,true);
    }

    public static void init(Application app, boolean debug) {
        setApplication(app);
        setDebug(debug);
        initDb(app);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }


    private static void initDb(Application app){
        AbAppUtil.importDatabase(app, "lib", R.raw.lib);
    }
}
