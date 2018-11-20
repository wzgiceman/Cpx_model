package com.base.muslim.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.base.library.R;
import com.base.library.utils.AbToastUtil;
import com.base.library.utils.DataReportUtils;


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
     * 显示toast提示信息
     *
     * @param msg
     */
    protected void showToast(int msg) {
        AbToastUtil.showToast(getContext(),msg);
    }

    /**
     * 显示toast提示信息
     *
     * @param msg
     */
    protected void showToast(String msg) {
        AbToastUtil.showToast(getContext(),msg);
    }

    @Override
    public void onClick(View v) {
    }

//    /**
//     * 跳转到指定的activity
//     *
//     * @param cls
//     */
//    public void jumpActivity(Class cls) {
//        Intent intent = new Intent(getContext(), cls);
//        getContext().startActivity(intent);
//    }
//
//    /**
//     * 带参数跳转到指定的activity
//     *
//     * @param cls
//     * @param bundle
//     */
//    public void jumpActivity(Class cls, Bundle bundle) {
//        Intent intent = new Intent(getContext(), cls);
//        intent.putExtras(bundle);
//        getContext().startActivity(intent);
//    }




    /**
     * 获取String.xml 中字符串
     * @param id
     * @return
     */
    public String getString(@StringRes int id){
        return getContext().getResources().getString(id);
    }

    /**
     * 获取String.xml 中字符串
     * @param id
     * @param formatArgs
     * @return
     */
    public String getString(@StringRes int id, Object... formatArgs){
        return getContext().getResources().getString(id,formatArgs);
    }


    /**
     * 统计埋点
     *
     * @param key
     */
    public void collectionFireabse(@NonNull int key) {
        collectionFireabse(getString(key));
    }

    public void collectionFireabse(@NonNull String key) {
        DataReportUtils.getInstance().report(key);
    }

    /**
     * 统计埋点
     *
     * @param key
     * @param bundle
     */
    public void collectionFireabse(@NonNull int key, Bundle bundle) {
        collectionFireabse(getString(key), bundle);
    }

    public void collectionFireabse(@NonNull String key, Bundle bundle) {
        DataReportUtils.getInstance().report(key, bundle);
    }
}
