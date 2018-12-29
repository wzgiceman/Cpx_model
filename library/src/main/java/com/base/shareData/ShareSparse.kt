package com.base.shareData

import android.util.SparseArray
import com.alibaba.fastjson.JSONObject
import com.base.shareData.message.ShareData
import com.base.shareData.message.ShareDataDb
import com.base.shareData.user.User

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
    private val PROJECT_DATA: SparseArray<Any> = SparseArray()

    /**
     * 根据上面定义的常量获取对象
     * @param key 关键key
     */
    fun getValueBy(key: String): Any {
        if (null == PROJECT_DATA.get(key.toInt())) {
            val value = getDbValueBy(key) ?: createEmptyValue(key)
            putValue(key, value)
        }
        return PROJECT_DATA.get(key.toInt())
    }

    /**
     * 保存数据到本地
     *
     * @param key key
     * @param any 数据源
     * @param saveLocal 是否需要保存到本地数据库
     */
    fun putValue(key: String, any: Any, saveLocal: Boolean = false) {
        if (saveLocal) {
            putDbValue(key, any)
        }
        PROJECT_DATA.put(key.toInt(), any)
    }


    /**
     * 创建key 创建空的value
     */
    private fun createEmptyValue(key: String): Any {
        return when (key) {
            USER_CLS -> {
                User()
            }
            else -> {
                Any()
            }
        }
    }

    /**
     * 根据上面定义的常量从数据库获取
     * @param key 关键key
     */
    private fun getDbValueBy(key: String): Any? {
        return ShareDataDb.getInstance().queryBy(key)
    }

    /**
     * set全局db变量
     * @param key 关键key
     * @param any 数据源
     */
    private fun putDbValue(key: String, any: Any) {
        ShareDataDb.getInstance().saveOrUpdate(ShareData(key, JSONObject.toJSONString(any)))
    }
}