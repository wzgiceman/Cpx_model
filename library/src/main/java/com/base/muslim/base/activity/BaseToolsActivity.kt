package com.base.muslim.base.activity

import android.app.ProgressDialog
import android.view.MotionEvent
import com.base.library.R
import com.base.muslim.base.IBaseTool
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
abstract class BaseToolsActivity : BaseActivity(), IBaseTool {
    private var loadingDialog: ProgressDialog? = null

    /**
     * 显示统一的加载框
     *
     * @param cancel 是否可以取消
     * @param title  显示的标题
     */
    protected fun showLoading(cancel: Boolean = true, title: String? = "") {
        if (!isValidActivity() || (loadingDialog != null && loadingDialog!!.isShowing)) return
        val message = if (title.isNullOrEmpty()) getString(R.string.Loading) else title
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
        if (isFinishing) {
            onRelease()
        }
        if (isFinishing && loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
    }

    /**
     * 在此回调中释放资源
     */
    open fun onRelease() {

    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        /**点击空白位置 隐藏软键盘 */
        checkKeyboard(ev)
        return super.dispatchTouchEvent(ev)
    }

    override fun initRecyclerView() {
    }

    override fun initViewPager() {
    }

//    override fun requestXXX() {
//    }

    override fun refreshView(data: Any?) {
    }

    override fun setData(vararg `object`: Any?) {
    }
}
