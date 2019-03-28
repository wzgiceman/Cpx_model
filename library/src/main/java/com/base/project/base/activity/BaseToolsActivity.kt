package com.base.project.base.activity

import android.app.ProgressDialog
import android.view.KeyEvent
import android.view.MotionEvent
import com.base.library.R
import com.base.library.utils.utilcode.util.ActivityUtils
import com.base.library.utils.utilcode.util.ToastUtils
import com.base.project.base.IBaseTool
import com.base.project.base.extension.checkKeyboard
import com.base.project.base.extension.isValidActivity
import com.base.project.base.extension.report


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
    /**双击退出程序开关,调用enableDoubleClickQuit启用双击返回键退出*/
    private var doubleClickQuit = false
    private var firstTime: Long = 0
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

    protected fun enableDoubleClickQuit() {
        doubleClickQuit = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (doubleClickQuit && keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.showShort("Press again to exit the program.")
                firstTime = System.currentTimeMillis()
            } else {
                report("app_quit")
                ActivityUtils.finishAllActivities()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
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
