package com.prog.zhigangwei.cpx_model.app

import android.content.ComponentCallbacks2
import android.support.multidex.MultiDexApplication
import com.base.ModelApp
import com.bumptech.glide.Glide
import com.prog.zhigangwei.cpx_model.BuildConfig

/**
 *
 *Describe:app入口
 *
 *Created by zhigang wei
 *on 2018/4/18
 *
 *Company :cpx
 */
class MApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initApp()
    }


    /**
     * 初始化一些启动数据
     */
    private fun initApp() {
//        CrashHandler.getInstance().init(this, MainActivity::class.java)
        ModelApp.init(this,BuildConfig.DEBUG)
    }

    /**
     * 表示应用程序正常运行，并且不会被杀掉。但是目前手机的内存已经非常低了，
     * 我们应该去释放掉一些不必要的资源以提升系统的性能，
     * 同时这也会直接影响到我们应用程序的性能。
     *
     * @param level
     */
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }


    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }


}