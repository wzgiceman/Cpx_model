package com.prog.zhigangwei.cpx_model.youtube.comment.comment

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.base.library.easyrecyclerview.adapter.BaseViewHolder
import com.base.library.easyrecyclerview.adapter.RecyclerArrayAdapter
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.youtube.common.bean.YouTubeCommentResult

/**
 * Description:
 * YouTubeAdapter
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
class YouTubeCommentAdapter(context: Context) : RecyclerArrayAdapter<YouTubeCommentResult.ContentBean.CommentsBean>(context) {
    override fun onCreateEasyViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<*> =
            YouTubeViewHolder(parent)

    internal class YouTubeViewHolder(parent: ViewGroup?) : BaseViewHolder<YouTubeCommentResult.ContentBean.CommentsBean>(parent, R.layout.item_you_tube_comment) {
        private val tvAuthor: TextView = `$`(R.id.tv_author)
        private val tvText: TextView = `$`(R.id.tv_text)

        override fun setData(comment: YouTubeCommentResult.ContentBean.CommentsBean) {
            super.setData(comment)
            tvAuthor.text = comment.author_name
            tvText.text = comment.text
        }
    }
}