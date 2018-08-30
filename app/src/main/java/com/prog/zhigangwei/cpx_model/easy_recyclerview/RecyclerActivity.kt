package com.prog.zhigangwei.cpx_model.easy_recyclerview

import android.support.v7.widget.LinearLayoutManager
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.easy_recyclerview.adapter.RecAdapter
import com.prog.zhigangwei.cpx_model.easy_recyclerview.adapter.footer.RcFooter
import com.prog.zhigangwei.cpx_model.easy_recyclerview.adapter.head.RcHead
import kotlinx.android.synthetic.main.activity_recycler.*


/**
 *
 *Describe:EasyRecyclerView使用界面
 *
 * 地址:https://github.com/Jude95/EasyRecyclerView
 *
 *Created by zhigang wei
 *on 2018/8/30
 *
 *Company :cpx
 */
class RecyclerActivity : BaseFragmentActivity() {
    private val adapter by lazy { RecAdapter(this) }


    override fun setContentViews() {
        setContentView(R.layout.activity_recycler)
        super.setContentViews()
    }

    override fun initResource() {
        for (item in 0..3) {
            adapter.add(RecyclerItemBean("位置$item", item, false))
        }
    }

    override fun initWidget() {
        initComplexWidget()
        initHead()
        initFooter()
    }


    override fun initComplexWidget() {
        super.initComplexWidget()
        erc.setLayoutManager(LinearLayoutManager(this))
        erc.adapter = adapter
        btn_head.setOnClickListener { initHead() }
        btn_footer.setOnClickListener { initFooter() }
    }

    /**
     * 添加头
     */
    private fun initHead() {
        adapter.addHeader(RcHead(RecyclerItemBean("header", 0, false)))
    }

    /**
     * 添加尾
     */
    private fun initFooter() {
        adapter.addFooter(RcFooter(RecyclerItemBean("footer", 0, false)))
    }

}