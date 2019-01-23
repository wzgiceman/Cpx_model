package com.base.project.base.fragment

import android.app.ProgressDialog
import com.base.library.R
import com.base.project.base.IBaseTool
import com.base.project.base.extension.isValidActivity


/**
 * fragment基础工具类
 * Created by WZG on 2016/1/28.
 */
abstract class BaseToolFragment : BaseLazyFragment(), IBaseTool {

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
            loadingDialog = ProgressDialog(context)
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

    override fun onDestroy() {
        super.onDestroy()
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
        }
    }

    override fun initRecyclerView() {
    }

    override fun initViewPager() {
    }

    override fun refreshView(data: Any?) {
    }

    override fun setData(vararg `object`: Any?) {
    }
}
