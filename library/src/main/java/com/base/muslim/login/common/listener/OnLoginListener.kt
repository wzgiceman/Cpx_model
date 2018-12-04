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
    /**
     * 登录成功
     * @param type 登录方式
     * @param token 获取到的token
     */
    fun onLoginSuccess(type: String, token: String)

    /**
     * 登录失败
     * @param type 登录方式
     * @param cause 失败原因
     */
    fun onLoginFail(type: String, cause: String)
}