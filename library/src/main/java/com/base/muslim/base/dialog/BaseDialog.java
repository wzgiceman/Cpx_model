package com.base.muslim.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.base.library.R;


/**
 * Dialog基础类
 *
 * @author WZG
 */
public abstract class BaseDialog extends Dialog implements OnClickListener {

    public BaseDialog(Context context) {
        super(context);
        Window window = this.getWindow();
        if (window == null) return;
        window.setWindowAnimations(R.style.dialog_animstyle);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    public BaseDialog(Context context, int style) {
        super(context, style);
        Window window = this.getWindow();
        if (window == null) return;
        window.setWindowAnimations(R.style.dialog_animstyle);
    }

    public BaseDialog(Context context, int style, boolean animal) {
        super(context, style);
        if (animal) {
            Window window = this.getWindow();
            if (window == null) return;
            window.setWindowAnimations(R.style.dialog_animstyle);
        }
    }

    /**
     * 初始化
     */
    protected void init(int layout) {
        setContentView(layout);
        initResource();
        initWidget();
    }

    /**
     * 初始化数据
     */
    protected abstract void initResource();

    /**
     * 初始化控件
     */
    protected abstract void initWidget();

    /**
     * 初始化固有控件
     */
    protected void initConstantWidget() {

    }

    @Override
    public void onClick(View v) {
    }
}
