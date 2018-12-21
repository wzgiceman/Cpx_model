package com.prog.zhigangwei.cpx_model.vpFragment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.widget.ImageView
import com.base.library.easyrecyclerview.adapter.BaseViewHolder
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.base.project.base.adapter.BaseHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.vpFragment.common.bean.Vedio
import java.util.*


/**
 * Describe:壁纸
 *
 *
 * Created by zhigang wei
 * on 2018/4/4
 *
 *
 * Company :cpx
 */
class VedioAdapter(context: Context) : RecyclerArrayAdapter<Vedio.EntriesBean>(context) {

    override fun onCreateEasyViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return WaterHolder(parent, Random())
    }


    /**
     * holder里面只能做和显示相关的处理
     * 操作的逻辑必须踢出到外层处理
     */
    internal class WaterHolder(parent: ViewGroup, private val random: Random) : BaseHolder<Vedio.EntriesBean>(parent, R.layout
            .recycler_item_vedio_holder_recycler) {
        private var ivWall: ImageView = `$`(R.id.iv_wall)

        @SuppressLint("ResourceType")
        override fun setData(data: Vedio.EntriesBean) {
            val drawa = ColorDrawable(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
            Glide.with(context).load(data.cover).apply(RequestOptions().placeholder(drawa))
                    .into(ivWall)
        }


    }


}