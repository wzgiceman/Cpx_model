package com.base.share_data

import android.util.SparseArray
import com.alibaba.fastjson.JSONObject
import com.base.share_data.share_msg.ShareData
import com.base.share_data.share_msg.ShareDataDb

/**
 *
 *Describe:少量得共享数据
 *
 *Created by zhigang wei
 *on 2018/5/23
 *
 *Company :cpx
 */
object ShareSparse {
    /**
     * USER类型
     */
    const val USER_CLS: String = "1"

    /**
     * 存放数据的对象
     */
    private val MUSLIM_DATA: SparseArray<Any> = SparseArray()

    /**
     * 根据上面定义的常量获取对象
     */
    fun getValueBy(key: String): Any {
        if (MUSLIM_DATA.get(key.toInt()) == null) {
            putValue(key, ShareDataDb.getInstance().queryBy(key))
        }
        return MUSLIM_DATA.get(key.toInt())
    }

    /**
     * 临时的全局变量
     * @param key 关键key
     * @param any 数据源
     */
    fun putValue(key: String, any: Any) {
        putValue(key, any, false)
    }

    /**
     * 是否需要保持数据到本地
     *
     * @param key key
     * @param any 数据源
     * @param saveLocal 是否需要缓冲处理
     */
    fun putValue(key: String, any: Any, saveLocal: Boolean) {
        if (saveLocal) {
            ShareDataDb.getInstance().savrOrUpdate(ShareData(key, JSONObject.toJSONString(any)))
        }
        MUSLIM_DATA.put(key.toInt(), any)
    }


}