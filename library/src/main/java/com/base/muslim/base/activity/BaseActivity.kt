package com.base.muslim.base.activity

import android.os.Bundle
import com.base.library.swipeBack.BaseSwipeBackActivity
import com.base.muslim.base.IBase

/**
 * Activity 基础类
 * Activity的模板方法，可滑动销毁父类
 *
 * @author WZG
 */
abstract class BaseActivity : BaseSwipeBackActivity(), IBase {
    /**上个界面传入的数据*/
    protected var bundle: Bundle? = null
    protected var beforeUi = true
    /**是否启用滑动返回*/
    private var enableSwipeBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveState(savedInstanceState)
        setBackEnable(enableSwipeBack)
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

    /*start------------------------bundle数据的恢复和保存-------------------------------*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        bundle ?: return
        outState.putBundle("bundle", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreSaveState(savedInstanceState)
    }

    /**
     * 复原数据
     *
     * @param savedInstanceState
     */
    protected fun restoreSaveState(savedInstanceState: Bundle?) {
        bundle = intent.extras
        if (bundle == null && savedInstanceState != null && savedInstanceState.containsKey("bundle")) {
            bundle = savedInstanceState.getBundle("bundle")
        }
    }
    /*end------------------------bundle数据的恢复和保存-------------------------------*/


}
