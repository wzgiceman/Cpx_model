package com.base.muslim.login

import android.content.Intent
import com.base.muslim.base.activity.BaseActivity

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
abstract class BaseLoginActivity : BaseActivity(), OnLoginListener {

    private val loginManager by lazy { LoginManager(this, this) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginManager.handleActivityResult(requestCode, resultCode, data)
    }

    fun loginBy(type: String) {
        loginManager.loginBy(type)
    }
}