package com.base.muslim.base

/**
 * Description:
 * 主要作用是方法命名规范
 * 在BaseFragmentToolsActivity和BaseToolFragment中实现了此接口
 * 需要调用相应方法时，重载即可
 *
 * 对于注释的方法，表示需要自己按需写
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/21
 */
open interface IBaseTool {

    /**
     * 初始化RecyclerView，如果需要传参数，需要自己写带参数的方法
     */
    fun initRecyclerView()

    /**
     * 初始化ViewPager，如果需要传参数，需要自己写带参数的方法
     */
    fun initViewPager()

    /**
     * Http请求，XXX表示请求的数据类型
     */
//    fun requestXXX()

    /**
     * 刷新界面，需要自己按需写参数类型
     */
    fun refreshView(data: Any?)

    /**
     * 设置数据，需要自己按需写参数类型
     */
    open fun setData(vararg `object`: Any?)
}
