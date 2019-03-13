package com.base

import android.app.Application
import android.util.Log
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.Utils
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import io.fabric.sdk.android.Fabric

/**
 * Description:
 * CPX Model中需要在Application中初始化的内容都在此类中初始化
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
object ModelApp {

    @JvmStatic
    fun init(app: Application, debug: Boolean = true) {
        RxRetrofitApp.init(app, debug)
        Utils.init(app)
        LogUtils.getConfig()
                .setConsoleSwitch(BuildConfig.DEBUG)
                .setLogSwitch(BuildConfig.DEBUG)
                .setGlobalTag("~~~")// 设置log全局标签，默认为空,当全局标签不为空时，我们输出的log全部为该tag，为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/logEvent/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.D)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.D)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1)// log栈深度，默认为1


        initTwitterLogin(app)
        initMAT(app)
        initFirebaseCrashlytics(app)
    }

    private fun initTwitterLogin(app: Application) {
        val config = TwitterConfig.Builder(app)
                .logger(DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(TwitterAuthConfig(app.getString(R.string.twitter_consumer_key),
                        app.getString(R.string.twitter_consumer_secret)))
                .debug(BuildConfig.DEBUG)
                .build()
        Twitter.initialize(config)
    }

    /**
     * 检测内存泄露
     */
    private fun initMAT(app: Application) {
        if (!BuildConfig.DEBUG) return
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return
        }
        LeakCanary.install(app)
    }

    /**
     * 初始化firebase崩溃统计
     */
    private fun initFirebaseCrashlytics(app:Application) {
        if (BuildConfig.DEBUG) return
        val fabric = Fabric.Builder(app)
            .kits(Crashlytics())
            .debuggable(true)           // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)
    }
}