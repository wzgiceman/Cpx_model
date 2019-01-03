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
abstract class BaseLazyFragment : BaseFragmentManagerFragment() {
    /**判断view是否创建*/
    private var viewCreated = false
    /**是否加载数据*/
    protected var loading = false
    /**
     * 字段说明：忽略[onViewCreated]中[loadData]前的 savedInstanceState == null 判断
     * 使用方法：专供FragmentStatePagerAdapter使用，使用了FragmentStatePagerAdapter的ViewPager，
     *      ViewPager中的子Fragment需要在[onCreateView]中将其设置为true（Kotlin文件可以在[init]方法中将其设置为true）
     * 原因：因为FragmentStatePagerAdapter会自动处理Fragment的状态保存与恢复
     *      但不会处理页面数据的保存与恢复，所以数据需要重新加载
     */
    protected var ignoreSavedState = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutId() != IBase.NO_LAYOUT) View.inflate(context, layoutId(), null) else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewCreated = true
        if (ignoreSavedState || null == savedInstanceState) {
            loadData()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        loadData()
    }

    /**
     * 加载数据
     */
    private fun loadData() {
        //如果可见,并且没有加载数据
        if (viewCreated && (this.isVisible || userVisibleHint) && !loading) {
            loading = true
            initFragment()
        }
    }

    /**
     * 重置加载状态
     * 数据懒加载取消/失败调用此方法，使得下次到达此页面时可以再次触发lazyLoadData刷新数据
     */
    open fun resetLoadingStatus() {
        loading = false
    }


    /**
     * 初始化fragment的根方法
     */
    protected fun initFragment() {
        initData()
        initView()
    }


    protected fun checkPrepared(): Boolean {
        return userVisibleHint && viewCreated && context != null
    }

}
