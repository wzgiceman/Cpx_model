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
    private var loadingStatus = LoadingStatusType.NotLoadYet

    /**数据加载状态枚举值*/
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
        if (checkVisible() && loadingStatus == LoadingStatusType.NotLoadYet) {
            loadingStatus = LoadingStatusType.Loading
            initData()
            initView()
        }
    }

    /**
     * 确定Fragment是否可见
     * 比系统的[isVisible]方法少了 view.windowToken == null 判断
     * 由于commit提交的Fragment的view.windowToken初始时为null，导致[isVisible]为false，无法懒加载数据
     * TODO 当然，最好的方法是使当前显示的Fragment的view.windowToken变为非空，暂时没有找到解决方法
     *
     */
    private fun checkVisible(): Boolean {
        return isAdded && !isHidden && view != null && view?.visibility == View.VISIBLE
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
}
