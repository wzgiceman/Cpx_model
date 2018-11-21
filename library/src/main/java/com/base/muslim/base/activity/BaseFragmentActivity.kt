package com.base.muslim.base.activity

import android.os.Bundle

import com.base.muslim.base.IBase

/**
 * fangmentactivity 自定义基础类
 * 可滑动销毁父类
 *
 * @author WZG
 */
abstract class BaseFragmentActivity : BaseFragmentToolsActivity(), IBase {

    protected var beforeUi = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveState(savedInstanceState)
        if (beforeUi) {
            initActivity()
        } else {
            initBeforeFailed()
        }
    }

    /**
     * 初始化Activity的根方法
     */
    protected open fun initActivity() {
        if (layoutId() != IBase.NO_LAYOUT) {
            setContentView(layoutId())
        }
        initData()
        initView()
    }

    /**
     * activity不初始化回掉处理
     */
    protected fun initBeforeFailed() {

    }

}
