package com.base.muslim.base.fragment

import android.os.Bundle
import com.base.muslim.base.IBaseSave

/**
 * Describe:需要记录保存状态,为了手动恢复数据
 *
 *
 * Created by zhigang wei
 * on 2017/6/2
 *
 *
 * Company :Sichuan Ziyan
 */
abstract class BaseSaveFragment : BaseFragment(), IBaseSave {

    protected var bundle: Bundle = Bundle()

    init {
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // Save State Here
        saveStateToArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Save State Here
        saveStateToArguments()
    }

    ////////////////////
    // Don't Touch !!存入的值用来校验是否执行过onSaveInstanceState
    ////////////////////

    private fun saveStateToArguments() {
        if (view != null) {
            val bundle: Bundle = saveState()
            if (bundle != null) {
                this.bundle = bundle
                val b = arguments
                b?.putBundle("internalSavedViewState8954201239547", bundle)
            }
        }

    }

    ////////////////////
    // Don't Touch !!校验是否执行过onSaveInstanceState
    ////////////////////

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (b != null) {
            val bundle = b.getBundle("internalSavedViewState8954201239547")
            if (bundle != null) {
                this.bundle = bundle
                restoreState()
                return true
            }
        }
        return false
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private fun restoreState() {
        val bundle = this.bundle ?: return
        // For Example
        //tv1.setText(bundle.getString("text"));
        onRestoreState(bundle)
    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private fun saveState(): Bundle {
        val state = Bundle()
        // For Example
        //state.putString("text", tv1.getText().toString());
        onSaveState(state)
        return state
    }

    override fun onFirstTimeLaunched() {
    }

    override fun onSaveState(outState: Bundle) {
    }

    override fun onRestoreState(savedInstanceState: Bundle) {
    }
}
