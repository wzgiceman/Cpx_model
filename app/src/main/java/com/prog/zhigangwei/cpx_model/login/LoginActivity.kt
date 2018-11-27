package com.prog.zhigangwei.cpx_model.login

import com.base.muslim.login.BaseLoginDialogActivity
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Description:
 * 登录页面Demo
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/26
 */
class LoginActivity : BaseLoginDialogActivity() {
    override fun layoutId() = R.layout.activity_login

    override fun initView() {
        btn_login_by_facebook.setOnClickListener { loginByFacebook() }
        btn_login_by_google.setOnClickListener { loginByGoogle() }
        btn_login_by_twitter.setOnClickListener { loginByTwitter() }
        btn_login_by_phone_or_email.setOnClickListener { loginByPhoneOrEmail() }
        btn_send_code.setOnClickListener { sendCode() }
    }

    override fun initData() {
    }

    override fun getAccount() = et_account.text.toString()

    override fun getVerificationCode() = et_verification_code.text.toString()

    override fun setBtnSendCodeEnabled(enabled: Boolean) {
        btn_send_code.isEnabled = enabled
    }

    override fun setBtnSendCodeText(text: String) {
        btn_send_code.text = text
    }

}
