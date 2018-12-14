package com.prog.zhigangwei.cpx_model.greendao

import com.base.project.base.activity.BaseActivity
import com.base.project.base.extension.showToast
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.greendao.greendao.DbBean
import com.prog.zhigangwei.cpx_model.greendao.greendao.DealDb
import kotlinx.android.synthetic.main.activity_greendao.*

/**
 *
 *Describe:数据库的使用
 *
 *Created by zhigang wei
 *on 2018/11/16
 *
 *Company :cpx
 */
class GreendaoActivity : BaseActivity() {

    private lateinit var dbBean: DbBean

    override fun layoutId() = R.layout.activity_greendao

    override fun initData() {
        dbBean = DbBean()
        dbBean.mail = "wzgiceman@gmail.com"
        dbBean.myId = "wzgiceman"
        dbBean.name = "zhigangwei"
    }

    override fun initView() {
        btn_add.setOnClickListener {
            DealDb.instance.insert(dbBean)
            showMsg(dbBean.id.toString())
        }
        btn_update.setOnClickListener {
            dbBean.name = "${dbBean.name} hello"
            DealDb.instance.update(dbBean)
            showMsg(dbBean.name.toString())
        }

        btn_addorupdate.setOnClickListener {
            dbBean.name = "${dbBean.name} world"
            DealDb.instance.savrOrUpdate(dbBean)
            showMsg(dbBean.name)
        }
        btn_query.setOnClickListener {
            DealDb.instance.queryBy(dbBean.myId)
            showMsg(dbBean.name)
        }
        btn_del.setOnClickListener {
            DealDb.instance.delete(dbBean.id)
            showMsg("del->${dbBean.id}")
        }
        btn_del_all.setOnClickListener {
            DealDb.instance.deleteAll()
            showMsg("del->all")
        }
        btn_query_all.setOnClickListener {
            showMsg("btn_query_all->${DealDb.instance.queryAll().size}")
        }
    }

    private fun showMsg(msg: String) {
        tv_msg.text = "msg:${msg}"
        showToast("suc")
    }

}