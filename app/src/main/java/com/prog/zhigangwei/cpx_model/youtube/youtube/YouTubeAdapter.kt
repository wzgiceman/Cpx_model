package com.prog.zhigangwei.cpx_model.youtube.youtube

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.base.library.easyrecyclerview.adapter.BaseViewHolder
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.youtube.common.bean.YouTubeVideoResult

/**
 * Description:
 * YouTubeAdapter
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeAdapter(context: Context) : RecyclerArrayAdapter<YouTubeVideoResult.ContentBean.ItemsBean>(context) {
    override fun onCreateEasyViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> =
            YouTubeViewHolder(parent)

    internal class YouTubeViewHolder(parent: ViewGroup?) : BaseViewHolder<YouTubeVideoResult.ContentBean.ItemsBean>(parent, R.layout.item_you_tube) {
        private val tvTitle: TextView = `$`(R.id.tv_title)
        private val ivThumbnail: ImageView = `$`(R.id.iv_thumbnail)

        init {
            addOnClickListener(R.id.tv_comment)
        }

        override fun setData(videoItem: YouTubeVideoResult.ContentBean.ItemsBean) {
            super.setData(videoItem)
            tvTitle.text = videoItem.title
            Glide.with(context)
                    .load(videoItem.thumbnail_url)
                    .apply(RequestOptions().placeholder(R.drawable.shape_bg_place_img))
                    .into(ivThumbnail)
        }
    }
}