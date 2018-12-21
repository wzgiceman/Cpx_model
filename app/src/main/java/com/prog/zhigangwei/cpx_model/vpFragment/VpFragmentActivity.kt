package com.prog.zhigangwei.cpx_model.vpFragment

import com.base.project.base.activity.BaseActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.vpFragment.vpFragment.SelectPageAdapter
import kotlinx.android.synthetic.main.activity_vp_fragment.*

/**
 *
 *Describe:vp和fragment结合使用demo
 *
 *Created by zhigang wei
 *on 2018/12/21
 *
 *Company :cpx
 */
class VpFragmentActivity : BaseActivity() {


    override fun layoutId() = R.layout.activity_vp_fragment


    override fun initData() {
    }

    override fun initView() {
        tab_layout_channel_title.setupWithViewPager(view_pager_channel)
        var adapter = SelectPageAdapter(supportFragmentManager)
        view_pager_channel.adapter = adapter
    }

}
