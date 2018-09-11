package com.prog.zhigangwei.cpx_model.easy_recyclerview.adapter.footer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.easy_recyclerview.RecyclerItemBean

/**
 * Describe:RcFooter
 *
 *
 * Created by zhigang wei
 * on 2018/4/3
 *
 *
 * Company :cpx
 */
class RcFooter constructor(val data: RecyclerItemBean) : RecyclerArrayAdapter.ItemView {
    lateinit var context: Context

    override fun onCreateView(parent: ViewGroup): View {
        context = parent.context
        return LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_footer_recycler, parent, false)
    }


    override fun onBindView(headerView: View) {
        val tv = headerView.findViewById<TextView>(R.id.tv_msg)

        /*head/footer在显示和隐藏的时候回重复的销毁和创建,所以界面的显示需要和数据源绑定*/
        tv.text = data.name
    }

}
