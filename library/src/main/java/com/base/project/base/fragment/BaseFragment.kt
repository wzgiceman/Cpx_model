package com.base.project.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.library.rxlifecycle.components.support.RxFragment
import com.base.project.base.IBase
import com.base.project.base.IBase.Companion.NO_LAYOUT


/**
 * 自定义fragment基础类
 * 非懒加载模式
 *
 * @author WZG
 */
abstract class BaseFragment : RxFragment(), IBase {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (layoutId() != NO_LAYOUT) View.inflate(context, layoutId(), null) else null
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
