package com.prog.zhigangwei.cpx_model

import com.base.library.utils.utilcode.util.LogUtils
import com.base.project.base.activity.BaseToolsActivity
import com.base.project.base.extension.jumpActivity
import com.base.project.base.extension.setOnRxClickListener
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
import com.prog.zhigangwei.cpx_model.vpFragment.VpFragmentActivity
import com.prog.zhigangwei.cpx_model.youtube.YouTubeActivity
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
class MainActivity : BaseToolsActivity() {

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
        btn_http.setOnRxClickListener { jumpActivity(HttpActivity::class.java) }
        btn_permission.setOnRxClickListener { jumpActivity(PermisssionActivity::class.java) }
        btn_img.setOnRxClickListener { jumpActivity(ImageActivity::class.java) }
        btn_RxBus.setOnRxClickListener { jumpActivity(RxBusActivity::class.java) }
        btn_EasyRecyclerView.setOnRxClickListener { jumpActivity(RecyclerActivity::class.java) }
        btn_greendao.setOnRxClickListener { jumpActivity(GreendaoActivity::class.java) }
        btn_rxjava.setOnRxClickListener { jumpActivity(RxJavaActivity::class.java) }
        btn_constraintLayout.setOnRxClickListener { jumpActivity(ConstraintLayoutActivity::class.java) }
        btn_login.setOnRxClickListener { jumpActivity(LoginActivity::class.java) }
        btn_share.setOnRxClickListener { jumpActivity(ShareActivity::class.java) }
        btn_rx_click.setOnRxClickListener { LogUtils.d("---->1秒内只会打印一次") }
        btn_vpfg.setOnRxClickListener { jumpActivity(VpFragmentActivity::class.java) }
        btn_youtube.setOnRxClickListener { jumpActivity(YouTubeActivity::class.java) }
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
