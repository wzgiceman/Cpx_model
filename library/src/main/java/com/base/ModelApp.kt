package com.base

import android.app.Application
import android.util.Log
import com.base.library.BuildConfig
import com.base.library.R
import com.base.library.rxRetrofit.RxRetrofitApp
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.Utils
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
object ModelApp {

    @JvmOverloads
    fun init(app: Application, debug: Boolean = true) {
        RxRetrofitApp.init(app, debug)
        Utils.init(app)
        LogUtils.getConfig().setGlobalTag("~~~")
                .setConsoleSwitch(BuildConfig.DEBUG)
                .setLogSwitch(BuildConfig.DEBUG)
        initTwitterLogin(app)
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
}