package com.base.muslim.base.fragment;

import android.os.Bundle;
import android.view.View;


/**
 * 自定义fragment基础类
 *
 * @author WZG
 */
public abstract class BaseFragment extends BaseSaveFragment {
    /**
     * 是否加载完成的标识
     */
    protected boolean createFinish = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initFragment();
        }
    }


    /**
     * 初始化fragment的根方法
     */
    protected void initFragment() {
        initResource();
        initWidget();
    }


    /**
     * 初始化数据
     */
    protected abstract void initResource();

    /**
     * 初始化基础控件
     */
    protected abstract void initWidget();

    /**
     * 其他第三方复杂控件初始化
     */
    protected void initComplexWidget() {

    }

    /**
     * 设置数据
     *
     * @param object
     */
    public void setData(Object... object) {

    }


}
