package com.prog.zhigangwei.cpx_model.easyRecyclerView

import android.support.v7.widget.LinearLayoutManager
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
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
class RecyclerActivity :BaseFragmentActivity(){
    private val adapter by lazy { RecAdapter(this) }


    override fun setContentViews() {
        setContentView(R.layout.activity_recycler)
        super.setContentViews()
    }

    override fun initResource() {
        for(item in 0..3){
            adapter.add(RecyclerItemBean("位置$item",item,false))
        }
    }

    override fun initWidget() {
        erc.setLayoutManager(LinearLayoutManager(this))
        erc.adapter=adapter
    }



}