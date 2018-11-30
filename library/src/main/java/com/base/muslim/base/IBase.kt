package com.base.muslim.base

/**
 * Description:
 * Base接口，所有可视界面的模板方法
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */
interface IBase {
    companion object {
        const val NO_LAYOUT = -1
    }

    /**
     * 获取布局id,使用[NO_LAYOUT]表示不加载布局
     */
    fun layoutId(): Int

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 初始化界面
     */
    fun initView()
}