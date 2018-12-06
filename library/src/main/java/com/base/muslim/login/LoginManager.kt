package com.base.muslim.login

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.constants.LoginConstants.Companion.GOOGLE
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.base.muslim.login.common.listener.OnLoginListener
import com.base.muslim.login.facebook.FacebookLoginManager
import com.base.muslim.login.google.GoogleLoginManager
import com.base.muslim.login.twitter.TwitterLoginManager

/**
 * Description:
 * 登录管理类
 *
 * Facebook登录，需要用浏览器打开 Facebook开发者平台：https://developers.facebook.com/apps/
 * 1.创建应用，将应用编号赋值给 gradle.properties 的 facebook_app_id 变量
 * 2.在Facebook开发者平台控制台的 设置->基本 中添加Android平台，填入Google Play包名、类名、密钥散列、隐私权政策、启用单点登录并保存
 * 4.在Facebook开发者平台控制台公开发布应用
 *
 * Google登录，需要在firebase控制台创建项目：firebase.com
 * 1.身份验证中，启用Google登录
 * 2.项目设置中，填入SHA1和包名
 * 3.下载google_service.json，放入app模块下
 * 4.在 google-service.json 中找到网页客户端ID，将其赋值给 gradle.properties 的 google_web_client_id 变量
 *
 * Twitter登录，需要在Twitter开发者平台：https://developer.twitter.com/en/apps
 * 1.创建应用，填写app名，描述，勾选"启用twitter登录"，填写隐私权政策,team url
 * 2.在callback url中添加：twittersdk://
 * 3.将API Key赋值给 gradle.properties 的twitter_consumer_key，API secret key赋值给 gradle.properties 的twitter_consumer_secret
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
class LoginManager(val activity: FragmentActivity,private val onLoginListener: OnLoginListener) {
    private val facebookLoginManager by lazy { FacebookLoginManager(activity, onLoginListener) }
    private val googleLoginManager by lazy { GoogleLoginManager(activity, onLoginListener) }
    private val twitterLoginManager by lazy { TwitterLoginManager(activity, onLoginListener) }

    fun loginBy(type: String) {
        when (type) {
            FACEBOOK -> facebookLoginManager.login()
            GOOGLE -> googleLoginManager.login()
            TWITTER -> twitterLoginManager.login()
        }
    }

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        facebookLoginManager.handleActivityResult(requestCode, resultCode, data)
        googleLoginManager.handleActivityResult(requestCode, data)
        twitterLoginManager.handleActivityResult(requestCode, resultCode, data)
    }

    fun release() {
        facebookLoginManager.release()
    }
}