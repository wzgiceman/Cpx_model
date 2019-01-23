package com.base.project.base.activity

import android.support.v4.app.Fragment
import com.base.library.R
import com.base.library.utils.utilcode.util.FragmentUtils
import com.base.project.base.fragment.BaseLazyFragment


/**
 * Fragment基础管理类
 * 系统方法
 *
 * Created by WZG on 2016/1/28.
 */
abstract class BaseFragmentManagerActivity : BaseToolsActivity() {
    /**当前显示的位置 */
    protected var show = 0
    /**tab页 */
    private var fragmentList: List<BaseLazyFragment>? = null

    /**
     * 设置当前tab里显示的fragment页面
     *
     * @param fragment
     */
    protected fun setFragment(layout: Int, fragment: Fragment) {
        FragmentUtils.replace(supportFragmentManager, fragment, layout, R.anim.fragment_enter, R.anim.fade_out)
    }

    /**
     * 初始化fragment显示界面
     *
     * @param containerId
     * @param fragmentList
     */
    protected fun initFragmentList(containerId: Int, fragmentList: List<BaseLazyFragment>): BaseLazyFragment {
        this.fragmentList = fragmentList
        FragmentUtils.add(supportFragmentManager, fragmentList, containerId, show)
        return fragmentList[show]
    }

    /**
     * 指定显示的fragment位置，show之前必须先initFragment将其add进来
     *
     * @param index
     */
    protected fun showFragment(index: Int): BaseLazyFragment? {
        val fragmentList = this.fragmentList
        if (fragmentList == null || fragmentList.isEmpty() || index < 0 || index >= fragmentList.size) return null
        if (show == index) return fragmentList[index]
        val fragment = fragmentList[index]
        FragmentUtils.showHide(fragment, fragmentList)
        show = index
        return fragment
    }
}
