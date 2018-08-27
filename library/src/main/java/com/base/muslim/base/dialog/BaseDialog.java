package com.base.muslim.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

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
        window.setWindowAnimations(R.style.dialog_animstyle);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public BaseDialog(Context context, int style) {
        super(context, style);
        Window window = this.getWindow();
        window.setWindowAnimations(R.style.dialog_animstyle);
    }

    public BaseDialog(Context context, int style, boolean animal) {
        super(context, style);
        if(animal){
            Window window = this.getWindow();
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

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 跳转到指定的activity
     *
     * @param cls
     */
    public void jumpActivity(Class cls) {
        Intent intent = new Intent(getContext(), cls);
        getContext().startActivity(intent);
    }

    /**
     * 带参数跳转到指定的activity
     *
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getContext(), cls);
        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }
}
