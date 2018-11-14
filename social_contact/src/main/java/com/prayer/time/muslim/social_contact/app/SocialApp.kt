package com.prayer.time.muslim.social_contact.app

import android.support.multidex.MultiDexApplication
import com.base.library.rxRetrofit.RxRetrofitApp
import com.prayer.time.muslim.social_contact.BuildConfig
import com.squareup.leakcanary.LeakCanary

/**
 *
 *Describe:
 *
 *Created by zhigang wei
 *on 2018/5/16
 *
 *Company :cpx
 */
class SocialApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        RxRetrofitApp.init(this, BuildConfig.DEBUG)
        initMAT()
    }


    /**
     * 检测内存泄露
     */
    private fun initMAT() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}