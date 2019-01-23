package com.base.project.base.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.utils.utilcode.util.LogUtils
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
    /**判断view是否创建*/
    private var viewCreated = false
    /**数据加载状态*/
    protected var loadingStatus = LoadingStatusType.NotLoadYet

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
        viewCreated = true
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
        LogUtils.d("${arguments.getString("key")}-->viewCreated:$viewCreated\tisHidden:$isHidden\tuserVisibleHint:$userVisibleHint\tloadingStatus:$loadingStatus")
        if (viewCreated && !isHidden && loadingStatus == LoadingStatusType.NotLoadYet) {
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
        // View销毁时，重置viewCreated
        viewCreated = false
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
