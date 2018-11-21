package com.base.muslim.base.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.base.muslim.base.IBase


/**
 * 懒加载的fragment
 * 使用方法：
 * 1.实现[.lazyLoadData]方法,在该方法中调用懒加载数据的方法，
 * 2.在懒加载数据请求成功后调用[.resetLoadingStatus]
 *
 * @author xuechao
 * @date 2018/9/26 上午9:32
 * @copyright cpx
 */

abstract class BaseLazyFragment : BaseSaveFragment(), IBase {
    /**是否加载数据*/
    private var loading = false
    /**判断view是否创建*/
    private var viewCreated = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutId() != IBase.NO_LAYOUT) View.inflate(context, layoutId(), null) else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewCreated = true
        if (savedInstanceState == null) {
            initFragment()
        }
    }


    /**
     * 初始化fragment的根方法
     */
    protected fun initFragment() {
        initData()
        loadData()
        initView()
    }

    /**
     * 懒加载数据
     */
    protected abstract fun lazyLoadData()

    /**
     * 设置数据
     *
     * @param object
     */
    fun setData(vararg `object`: Any) {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //如果view没有创建或者不可见，则不能加载数据
        if (viewCreated && isVisibleToUser) {
            loadData()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            loadData()
        }
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        //如果可见,并且没有加载数据
        if (this.userVisibleHint && !loading) {
            loading = true
            lazyLoadData()
        }
    }

    /**
     * 重置加载状态
     * 数据懒加载取消/失败调用此方法，使得下次到达此页面时可以再次触发lazyLoadData刷新数据
     */
    protected fun resetLoadingStatus() {
        loading = false
    }
}
