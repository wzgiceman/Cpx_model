package com.base.muslim.tipsdialog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.base.library.R
import com.base.muslim.base.adapter.BaseAdapters
import java.util.*

class TipsDialogAdapter(private val data: List<String>, private val mContext: Context) : BaseAdapters<String>(data) {

    override fun getLtData(): ArrayList<String> {
        return data as ArrayList<String>
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view = convertView
        var holder: TipsViewHolder
        if (view == null) {
            view = View.inflate(mContext, R.layout.recycler_item_holder_tips, null)
            holder = TipsViewHolder(view)
            view.tag = holder
        } else {
            holder = view.tag as TipsViewHolder
        }
        holder.tView.text = data[position]
        return view
    }

    internal inner class TipsViewHolder(itemView: View) {
        val tView: TextView = itemView as TextView
    }
}
