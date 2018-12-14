package com.base.muslim.tipsdialog

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import com.base.library.R
import com.base.muslim.base.dialog.BaseDialog
import kotlinx.android.synthetic.main.dialog_tips.*

/**
 * 弹窗
 */
class TipsDialog : BaseDialog {
    private var listener: OnDropBtnClickListener? = null
    private var listener1: OnBtnClickListener? = null
    private lateinit var adapter: TipsDialogAdapter
    private var type: Any = "type"
    private var okBtnText: String = context.getString(R.string.tips_dialog_Drop)

    /**
     * 使用的构造方法
     *
     * @param context
     * @param listener 此接口参数只有确定按钮的回调方法
     */
    constructor(context: Context, listener: OnDropBtnClickListener) : super(context) {
        this.listener = listener
        setDialogSystemLine()
    }

    /**
     * 使用的构造方法
     *
     * @param context
     * @param listener 此接口参数有确定和取消两个按钮的回调方法
     */
    constructor(context: Context, listener: OnBtnClickListener) : super(context) {
        this.listener1 = listener
        setDialogSystemLine()
    }

    /**
     * 适配低版本顶部的出现的蓝色横线问题
     */
    private fun setDialogSystemLine() {
        try {
            var dividerId = context.resources.getIdentifier("android:id/titleDivider", null, null)
            val divider: View = this.findViewById(dividerId)
            divider.setBackgroundColor(Color.TRANSPARENT) //横线透明色
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun layoutId() = R.layout.dialog_tips

    override fun initData() {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }

    override fun initView() {
        Cancel_btn.setOnClickListener(this)
        Drop_btn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.Cancel_btn -> {
                dismiss()
//                listener1?.let { listener1!!.onCancelBtnClick() }
            }
            R.id.Drop_btn -> {
                dismiss()
                listener?.let { listener!!.onClick(Drop_btn!!, type) }
                listener1?.let { listener1!!.onOkBtnClick() }
            }
        }
    }

    private fun setListViewHeight(tips: Array<out String>?) {
        if (tips == null)
            return
        if (tips.size > 4) {
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 480)
            ExitDialog_ListView.layoutParams = params
        }
    }

    /**
     * 显示弹窗
     * 参数1：确定按钮的文字，默认为Drop
     * 参数2：可变参数，弹窗的提示内容
     */
    fun showDialog(okBtnText: String?, vararg tipsText: String) {
        setListViewHeight(tipsText)
        okBtnText?.let { this.okBtnText = okBtnText }
        Drop_btn.text = this.okBtnText
        adapter = TipsDialogAdapter(tipsText.toList(), context)
        ExitDialog_ListView.adapter = adapter
        show()
    }

    /**
     * 显示弹窗
     * 参数1：确定按钮的文字，默认为Drop
     * 参数2：设置窗口类型dialog_tips.xml
     * 参数3：可变参数，弹窗的提示内容
     */
    fun showDialog(okBtnText: String?, type: Any, vararg tipsText: String) {
        setListViewHeight(tipsText)
        okBtnText?.let { this.okBtnText = okBtnText }
        Drop_btn.text = this.okBtnText
        adapter = TipsDialogAdapter(tipsText.toList(), context)
        ExitDialog_ListView.adapter = adapter
        this.type = type
        show()
    }

    /**
     * 显示弹窗
     */
    fun showDialog() {
        setListViewHeight(null)
        show()
    }

    /**
     * 同一个页面多个弹窗，设置弹窗类型，该类型值在点击确定按钮时返回
     */
    fun setDialogType(type: Any) {
        this.type = type
    }

    interface OnDropBtnClickListener {
        fun onClick(v: View, type: Any)
    }

    interface OnBtnClickListener {
        fun onCancelBtnClick()
        fun onOkBtnClick()
    }

}