package com.base.muslim.base

/**
 * Description:
 * 主要作用是方法命名规范
 * 在BaseFragmentToolsActivity和BaseToolFragment中实现了此接口
 * 需要调用相应方法时，重载即可
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/21
 */
interface IBaseTool {

    fun initRecyclerView()

    fun initViewPager()
}