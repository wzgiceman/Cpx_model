package com.base.project.base.fragment

import android.os.Bundle
import com.base.library.rxlifecycle.components.support.RxFragment
import com.base.project.base.IBaseSave

/**
 * Describe:需要记录保存状态,为了手动恢复数据
 *
 * Created by zhigang wei
 * on 2017/6/2
 * Company :Sichuan Ziyan
 */
abstract class BaseSaveFragment : RxFragment(), IBaseSave {

    protected var bundle: Bundle = Bundle()

    init {
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isFirstLaunch()) {
            onFirstTimeLaunched()
        } else {
            // 如果不是第一次创建，取出保存的状态值，并恢复状态
            bundle = arguments?.getBundle("internalSavedViewState8954201239547")!!
            onRestoreState(bundle)
        }
    }

    /**
     * 判断该Fragment是否是第一次创建
     */
    private fun isFirstLaunch(): Boolean {
        return arguments?.getBundle("internalSavedViewState8954201239547") == null
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        saveStateToArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveStateToArguments()
    }

    /**
     * 保存状态
     * [onSaveInstanceState]和[onDestroyView]时执行状态的保存
     */
    private fun saveStateToArguments() {
        if (view == null) return
        bundle.clear()
        onSaveState(bundle)
        arguments?.putBundle("internalSavedViewState8954201239547", bundle)
    }

    override fun onFirstTimeLaunched() {
    }

    override fun onSaveState(outState: Bundle) {
        // For Example
        //state.putString("text", tv1.getText().toString());
    }

    override fun onRestoreState(savedInstanceState: Bundle) {
        // For Example
        //tv1.setText(bundle.getString("text"));
    }
}
