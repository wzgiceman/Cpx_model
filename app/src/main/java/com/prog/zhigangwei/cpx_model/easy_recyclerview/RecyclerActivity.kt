package com.prog.zhigangwei.cpx_model.easy_recyclerview

import android.support.v7.widget.LinearLayoutManager
import com.base.library.retrofit_rx.observer.AbsObserver
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.easy_recyclerview.test_adapter.RecAdapter
import com.prog.zhigangwei.cpx_model.easy_recyclerview.test_adapter.footer.RcFooter
import com.prog.zhigangwei.cpx_model.easy_recyclerview.test_adapter.head.RcHead
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
class RecyclerActivity : BaseFragmentActivity() {
    private val adapter by lazy { RecAdapter(this) }


    override fun setContentViews() {
        setContentView(R.layout.activity_recycler)
        super.setContentViews()
    }

    override fun initResource() {
        for (item in 0..3) {
            adapter.add(RecyclerItemBean("位置$item", item, false))
        }
    }

    override fun initWidget() {
        initComplexWidget()
        initHead()
        initFooter()
    }


    override fun initComplexWidget() {
        super.initComplexWidget()
        erc.setLayoutManager(LinearLayoutManager(this))
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
    }

    /**
     * 添加尾
     */
    private fun initFooter() {
        adapter.addFooter(RcFooter(RecyclerItemBean("footer", 0, false)))
    }

}