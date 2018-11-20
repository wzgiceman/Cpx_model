package com.prayer.time.muslim.social_contact.ui

import com.base.muslim.base.activity.BaseFragmentActivity
import com.prayer.time.muslim.social_contact.R
import kotlinx.android.synthetic.main.social_contact_activity_main.*


/**
 *
 *Describe:
 *
 *Created by zhigang wei
 *on 2018/5/16
 *
 *Company :cpx
 */
class TestActivity : BaseFragmentActivity() {
    override fun layoutId() = R.layout.social_contact_activity_main

    override fun initData() {
    }

    override fun initView() {
        tv_ma.text="hello"
    }
}