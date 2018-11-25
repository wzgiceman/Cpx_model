package com.base.muslim.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.rxlifecycle.components.support.RxFragment
import com.base.muslim.base.IBase
import com.base.muslim.base.IBase.Companion.NO_LAYOUT


/**
 * 自定义fragment基础类
 *
 * @author WZG
 */
abstract class BaseFragment : RxFragment(), IBase {
    /**
     * 是否加载完成的标识
     */
    protected var createFinish = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutId() != NO_LAYOUT) View.inflate(context, layoutId(), null) else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            initFragment()
        }
    }

    /**
     * 初始化fragment的根方法
     */
    private fun initFragment() {
        initData()
        initView()
    }
}
