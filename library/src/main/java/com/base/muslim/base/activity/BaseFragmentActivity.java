package com.base.muslim.base.activity;

import android.os.Bundle;

/**
 * fangmentactivity 自定义基础类
 * 可滑动销毁父类
 *
 * @author WZG
 */
public abstract class BaseFragmentActivity extends BaseFragmentToolsActivity {

    protected boolean beforUi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resotreSaveState(savedInstanceState);
        if (beforUi) {
            initActivity();
        } else {
            initBeforFaild();
        }
    }

    /**
     * 初始化Activity的根方法
     */

    protected void initActivity() {
        setContentViews();
        initResource();
        initWidget();
    }


    /**
     * 设置绑定view
     */
    protected void setContentViews() {
    }

    /**
     * 初始化数据
     */
    protected abstract void initResource();

    /**
     * 基本控件初始化
     */
    protected abstract void initWidget();

    /**
     * 其他第三方复杂控件初始化
     */
    protected void initComplexWidget() {

    }


    /**
     * activity不初始化回掉处理
     */
    protected void initBeforFaild() {

    }



}
