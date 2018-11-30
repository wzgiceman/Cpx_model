package com.base.muslim.base.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.base.library.R
import com.base.muslim.base.IBase


/**
 * Dialog基础类
 *
 * @author WZG
 */
abstract class BaseDialog @JvmOverloads constructor(context: Context, style: Int = R.style.custom_dialog2, animal: Boolean = true) : Dialog(context, style), OnClickListener, IBase {

    init {
        if (animal) {
            val window = this.window
            window?.setWindowAnimations(R.style.dialog_animstyle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * 初始化
     */
    protected fun init() {
        if (IBase.NO_LAYOUT != layoutId()) {
            setContentView(layoutId())
        }
        initData()
        initView()
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun onClick(v: View) {}
}
