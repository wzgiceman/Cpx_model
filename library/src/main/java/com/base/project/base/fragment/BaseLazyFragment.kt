package com.base.project.base.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.project.base.IBase

/**
 * Description:
 * Fragment基类，懒加载
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/9/25
 */
abstract class BaseLazyFragment : BaseSaveFragment(), IBase {
    /**数据加载状态*/
    protected var loadingStatus = LoadingStatusType.NotLoadYet
    /**
     * 懒加载开关，设置为true时不使用懒加载
     *
     * 注：1.对于TabLayout绑定的Fragment，在
     * [BaseFragmentManagerFragment]的[BaseFragmentManagerFragment.initFragmentList]方法和
     * [com.base.project.base.activity.BaseFragmentManagerActivity]的
     * [com.base.project.base.activity.BaseFragmentManagerActivity.initFragmentList]方法
     * 中将第一个Fragment的[initNow]设置为了true，使得进入时加载数据
     * 因为这种情况下第一个Fragment的isVisible为false，不设置此参数的话，[initData]无法加载
     * 2.ViewPager无需设置此参数，ViewPager默认相邻的两个Fragment的[isVisible]为true
     * 3.使用者无需关心此参数
     */
    private var initNow = false

    /**数据加载状态*/
    enum class LoadingStatusType {
        //尚未加载
        NotLoadYet,
        //加载中
        Loading,
        //加载完成
        LoadFinish
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutId() != IBase.NO_LAYOUT) View.inflate(context, layoutId(), null) else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        loadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        loadData()
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        //如果可见,并且没有加载数据
        if ((initNow || isVisible) && loadingStatus == LoadingStatusType.NotLoadYet) {
            loadingStatus = LoadingStatusType.Loading
            initData()
            initView()
        }
    }

    /**
     * 恢复数据
     * fragment销毁时，fragment中的数据不会被销毁
     * 当fragment重新创建时，将数据再次设置到view中
     */
    override fun onRestoreState(savedInstanceState: Bundle) {
        super.onRestoreState(savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 如果数据仍然在加载，View就被销毁了，则重置loadingStatus，使得下次到达此页面重新加载数据
        if (loadingStatus == LoadingStatusType.Loading) {
            resetLoadingStatus()
        }
    }

    /**
     * 重置加载状态
     * 数据懒加载取消/失败调用此方法，使得下次到达此页面时刷新数据
     */
    open fun resetLoadingStatus() {
        loadingStatus = LoadingStatusType.NotLoadYet
    }

    /**
     * 加载状态设置为加载完成
     */
    open fun loadingFinish() {
        loadingStatus = LoadingStatusType.LoadFinish
    }

    /**
     * 立即初始化，不使用懒加载
     */
    fun initNow() {
        initNow = true
    }
}
