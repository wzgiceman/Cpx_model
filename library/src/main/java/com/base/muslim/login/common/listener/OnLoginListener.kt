package com.base.muslim.login.common.listener

/**
 * Description:
 * 登录接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
interface OnLoginListener {
    fun onLoginSuccess(type: String, token: String)
    fun onLoginFail(type: String, cause: String)
}