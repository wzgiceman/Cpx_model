package com.prog.zhigangwei.cpx_model.easyRecyclerview.easyRecyclerview

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.base.library.easyrecyclerview.adapter.BaseViewHolder
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.base.library.utils.utilcode.util.LogUtils
import com.base.muslim.base.adapter.BaseHolder
import com.base.muslim.weak.Weak
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.easyRecyclerview.common.bean.RecyclerItemBean


/**
 * Describe:adapter的使用
 *
 *
 * Created by zhigang wei
 * on 2018/4/4
 *
 *
 * Company :cpx
 */
class RecAdapter(context: Context) : RecyclerArrayAdapter<RecyclerItemBean>(context) {

    override fun onCreateEasyViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        /*弱引用防止泄露*/
        val adapter by Weak {
            this
        }
        return WaterHolder(parent, adapter!!)
    }

    /*记录历史位置信息-方便刷新*/
    private var olderPosition = -1

    /**
     * item里面处理的事件具体处理位置亦可以放入到adapter的实现层,保证处理逻辑在外部统一处理(activity或者fragment等)
     * @param position 处理完数据后刷新的位置
     */
    fun doSomething(position: Int) {
        LogUtils.d("position = $position, olderPosition = $olderPosition")
        /*这里如果存在head或者footer在获取数据的时候需要减去head的位置量*/
        getItem(position - headerCount).choose = true
        /*处理完数据以后更新位置数据*/
        notifyItemChanged(position)

        /*手动刷新需要刷新的位置,避免全局刷新耗时*/
        if (olderPosition != -1 && olderPosition != position) {
            getItem(olderPosition - headerCount).choose = false
            notifyItemChanged(olderPosition)
        }
        olderPosition = position
    }

    /**
     * 新增了header之后调用，保证item更新时位置正确
     */
    fun increaseOlderPosition() {
        if (olderPosition != -1) {
            olderPosition++
        }
    }

    /**
     * holder里面只能做和显示相关的处理
     * 操作的逻辑必须踢出到外层处理
     */
    internal class WaterHolder(parent: ViewGroup, private val adapter: RecAdapter) : BaseHolder<RecyclerItemBean>(parent, R.layout
            .recycler_item_holder_recycler) {

        private var tv: TextView = `$`(R.id.tv_msg)
        private var btn: Button = `$`(R.id.btn_test)

        override fun setData(data: RecyclerItemBean) {
            tv.setTextColor(if (data.choose) context.resources.getColor(R.color.c_008000) else context.resources.getColor(R.color
                    .c_0571FA))
            tv.text = data.name
            btn.setOnClickListener { adapter.doSomething(adapterPosition) }
        }
    }
}