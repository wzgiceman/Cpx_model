package com.prog.zhigangwei.cpx_model.permission

import com.base.library.rxPermissions.RxPermissions
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_permission.*

/**
 *
 *Describe:权限请求
 *
 *Created by zhigang wei
 *on 2018/8/24
 *
 *Company :cpx
 */
class PermisssionActivity : BaseFragmentActivity() {


    override fun setContentViews() {
        setContentView(R.layout.activity_permission)
        super.setContentViews()
    }

    override fun initResource() {
    }

    override fun initWidget() {
        btn_get.setOnClickListener {
            RxPermissions.getInstance(this).request(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe {
                        tv_msg.text = if (it) "RxPermissions suc" else "RxPermissions faild"
                    }
        }
    }

}
