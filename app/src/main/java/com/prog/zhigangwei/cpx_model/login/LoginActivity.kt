package com.prog.zhigangwei.cpx_model.login

import com.base.muslim.login.base.BaseLoginActivity
import com.base.muslim.login.common.constants.LoginConstants.Companion.FACEBOOK
import com.base.muslim.login.common.constants.LoginConstants.Companion.GOOGLE
import com.base.muslim.login.common.constants.LoginConstants.Companion.TWITTER
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Description:
 * 第三方 登录页面Demo
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
class LoginActivity : BaseLoginActivity() {
    override fun layoutId() = R.layout.activity_login

    override fun initView() {
        btn_login_by_facebook.setOnClickListener { loginBy(FACEBOOK) }
        btn_login_by_google.setOnClickListener { loginBy(GOOGLE) }
        btn_login_by_twitter.setOnClickListener { loginBy(TWITTER) }
    }

    override fun initData() {
    }

    override fun onLoginSuccess(type: String, token: String) {
        tv_login_status.append("$type 登录成功\ntoken:$token\n\n")
    }

    override fun onLoginFail(type: String, cause: String) {
        tv_login_status.append("$type 登录失败\ncause:$cause\n\n")
    }
}
