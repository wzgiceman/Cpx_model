package com.prog.zhigangwei.cpx_model

import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.muslim.base.extension.jumpActivity
import com.prog.zhigangwei.cpx_model.easyRecyclerview.RecyclerActivity
import com.prog.zhigangwei.cpx_model.greendao.GreendaoActivity
import com.prog.zhigangwei.cpx_model.http.HttpActivity
import com.prog.zhigangwei.cpx_model.image.ImageActivity
import com.prog.zhigangwei.cpx_model.permission.PermisssionActivity
import com.prog.zhigangwei.cpx_model.rxbus.RxBusActivity
import com.prog.zhigangwei.cpx_model.rxjava.RxJavaActivity
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
class MainActivity : BaseFragmentActivity() {
    override fun initActivity() {
        super.initActivity()
        initComplexWidget()
    }

    override fun layoutId() = R.layout.activity_main

    override fun initData() {
        initResourceA()
        initResourceB()
    }

    /**
     * 数据处理A
     */
    private fun initResourceA() {

    }


    /**
     * 数据处理B
     */
    private fun initResourceB() {

    }

    override fun initView() {
        btn_http.setOnClickListener { jumpActivity(HttpActivity::class.java) }
        btn_permission.setOnClickListener { jumpActivity(PermisssionActivity::class.java) }
        btn_img.setOnClickListener { jumpActivity(ImageActivity::class.java) }
        btn_RxBus.setOnClickListener { jumpActivity(RxBusActivity::class.java) }
        btn_EasyRecyclerView.setOnClickListener { jumpActivity(RecyclerActivity::class.java) }
        btn_greendao.setOnClickListener { jumpActivity(GreendaoActivity::class.java) }
        btn_rxjava.setOnClickListener { jumpActivity(RxJavaActivity::class.java) }
    }

    override fun initComplexWidget() {
        /*其他控件处理*/
        initWidgetA()
        initWidgetB()
    }


    /**
     * 界面处理A
     */
    private fun initWidgetA() {

    }


    /**
     * 界面处理B
     */
    private fun initWidgetB() {

    }
}
