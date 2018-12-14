package com.prog.zhigangwei.cpx_model

import com.base.muslim.base.activity.BaseActivity
import com.base.muslim.base.extension.jumpActivity
import com.prog.zhigangwei.cpx_model.constraintLayout.ConstraintLayoutActivity
import com.prog.zhigangwei.cpx_model.easyRecyclerview.RecyclerActivity
import com.prog.zhigangwei.cpx_model.greendao.GreendaoActivity
import com.prog.zhigangwei.cpx_model.http.HttpActivity
import com.prog.zhigangwei.cpx_model.image.ImageActivity
import com.prog.zhigangwei.cpx_model.login.LoginActivity
import com.prog.zhigangwei.cpx_model.permission.PermisssionActivity
import com.prog.zhigangwei.cpx_model.rxbus.RxBusActivity
import com.prog.zhigangwei.cpx_model.rxjava.RxJavaActivity
import com.prog.zhigangwei.cpx_model.share.ShareActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 *Describe:主界面
 *
 *Created by zhigang wei
 *on 2018/4/18
 *
 *Company :cpx
 */
class MainActivity : BaseActivity() {

    override fun layoutId() = R.layout.activity_main

    override fun initData() {
        initDataA()
        initDataB()
    }

    /**
     * 数据处理A
     */
    private fun initDataA() {

    }


    /**
     * 数据处理B
     */
    private fun initDataB() {

    }

    override fun initView() {
        initComplexWidget()
        initViewA()
        initViewB()
    }

    /**
     * 基本控件处理
     */
    private fun initComplexWidget() {
        setBackEnable(false)
        btn_http.setOnClickListener { jumpActivity(HttpActivity::class.java) }
        btn_permission.setOnClickListener { jumpActivity(PermisssionActivity::class.java) }
        btn_img.setOnClickListener { jumpActivity(ImageActivity::class.java) }
        btn_RxBus.setOnClickListener { jumpActivity(RxBusActivity::class.java) }
        btn_EasyRecyclerView.setOnClickListener { jumpActivity(RecyclerActivity::class.java) }
        btn_greendao.setOnClickListener { jumpActivity(GreendaoActivity::class.java) }
        btn_rxjava.setOnClickListener { jumpActivity(RxJavaActivity::class.java) }
        btn_constraintLayout.setOnClickListener { jumpActivity(ConstraintLayoutActivity::class.java) }
        btn_login.setOnClickListener { jumpActivity(LoginActivity::class.java) }
        btn_share.setOnClickListener { jumpActivity(ShareActivity::class.java) }
    }

    /**
     * 界面处理A
     */
    private fun initViewA() {

    }


    /**
     * 界面处理B
     */
    private fun initViewB() {

    }
}
