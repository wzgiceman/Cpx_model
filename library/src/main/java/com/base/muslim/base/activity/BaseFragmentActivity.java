package com.base.muslim.base.activity;

import android.os.Bundle;

import com.base.muslim.base.IBase;

/**
 * fangmentactivity 自定义基础类
 * 可滑动销毁父类
 *
 * @author WZG
 */
public abstract class BaseFragmentActivity extends BaseFragmentToolsActivity implements IBase {

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
        if (layoutId() != NO_LAYOUT) {
            setContentView(layoutId());
        }
        initData();
        initView();
    }

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
