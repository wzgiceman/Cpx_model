package com.prayer.time.muslim.socialContact.ui

import com.base.project.base.activity.BaseActivity
import com.prayer.time.muslim.socialContact.R
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
class TestActivity : BaseActivity() {
    override fun layoutId() = R.layout.social_contact_activity_main

    override fun initData() {
    }

    override fun initView() {
        tv_ma.text="hello"
    }
}