package com.prog.zhigangwei.cpx_model.easyRecyclerview

import android.support.v7.widget.LinearLayoutManager
import com.base.library.easyrecyclerview.decoration.SpaceDecoration
import com.base.library.observer.AbsObserver
import com.base.library.utils.utilcode.util.ConvertUtils
import com.base.project.base.activity.BaseToolsActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.easyRecyclerview.common.bean.RecyclerItemBean
import com.prog.zhigangwei.cpx_model.easyRecyclerview.easyRecyclerview.RecAdapter
import com.prog.zhigangwei.cpx_model.easyRecyclerview.easyRecyclerview.footer.RcFooter
import com.prog.zhigangwei.cpx_model.easyRecyclerview.easyRecyclerview.head.RcHead
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_recycler.*
import java.util.concurrent.TimeUnit

/**
 *
 *Describe:EasyRecyclerView使用界面
 *
 *Created by zhigang wei
 *on 2018/8/30
 *
 *Company :cpx
 *
 * 更多复杂用法处理参考:https://github.com/Jude95/EasyRecyclerView
 */
class RecyclerActivity : BaseToolsActivity() {

    private val adapter by lazy { RecAdapter(this) }

    override fun layoutId() = R.layout.activity_recycler

    override fun initData() {
        for (item in 0..3) {
            adapter.add(RecyclerItemBean("位置$item", item, false))
        }
    }

    override fun initView() {
        initRecyclerView()
        initHead()
        initFooter()
    }


    override fun initRecyclerView() {
        erc.setLayoutManager(LinearLayoutManager(this))

        val itemDecoration = SpaceDecoration(ConvertUtils.dp2px(8.0f))//参数是距离宽度
        itemDecoration.setPaddingEdgeSide(true)//是否为左右2边添加padding.默认true.
        itemDecoration.setPaddingStart(true)//是否在给第一行的item添加上padding(不包含header).默认true.
        itemDecoration.setPaddingHeaderFooter(false)//是否对Header于Footer有效,默认false.
        erc.addItemDecoration(itemDecoration)

        erc.adapter = adapter
        btn_head.setOnClickListener { initHead() }
        btn_footer.setOnClickListener { initFooter() }
        /*下拉刷新*/
        erc.setRefreshListener {
            Observable.timer(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : AbsObserver<Long>() {
                        override fun onNext(t: Long) {
                            adapter.add(RecyclerItemBean("位置xxx", System.currentTimeMillis().toInt(), false))
                            erc.setRefreshing(false)
                        }
                    })
        }
        /*加载更多*/
//        void setMore(final int res,OnMoreListener listener);
//        void setMore(final View view,OnMoreListener listener);
    }

    /**
     * 添加头
     */
    private fun initHead() {
        adapter.addHeader(RcHead(RecyclerItemBean("header", 0, false)))
        adapter.increaseOlderPosition()
    }

    /**
     * 添加尾
     */
    private fun initFooter() {
        adapter.addFooter(RcFooter(RecyclerItemBean("footer", 0, false)))
    }

}