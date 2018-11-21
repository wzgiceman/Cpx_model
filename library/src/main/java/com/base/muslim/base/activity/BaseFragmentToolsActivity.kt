package com.base.muslim.base.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import com.base.library.R
import com.base.muslim.base.extension.checkKeyboard
import com.base.muslim.base.extension.isValidActivity


/**
 * Describe:基础类工具类:公共方法
 *
 * Created by zhigang wei
 * on 2017/7/4.
 *
 * Company :Sichuan Ziyan
 */
open class BaseFragmentToolsActivity : BaseFragmentManagerActivity() {
    /**上个界面传入的数据*/
    protected var bundle: Bundle? = null
    private var loadingDialog: ProgressDialog? = null

    /**
     * 显示统一的加载框
     *
     * @param cancel 是否可以取消
     * @param title  显示的标题
     */
    protected fun showLoading(cancel: Boolean, title: String) {
        if (!isValidActivity() || (loadingDialog != null && loadingDialog!!.isShowing)) return
        val message = if (TextUtils.isEmpty(title)) getString(R.string.Loading) else title
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog(this)
        }
        loadingDialog?.setMessage(message)
        loadingDialog?.setCancelable(cancel)
        loadingDialog?.show()
    }

    /**
     * 关闭加载框
     */
    protected fun closeLoading() {
        if (!isValidActivity()) return
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing && loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
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

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        /**点击空白位置 隐藏软键盘 */
        checkKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }
}
