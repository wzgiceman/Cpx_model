package com.base.muslim.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.muslim.base.IBase;


/**
 * 自定义fragment基础类
 *
 * @author WZG
 */
public abstract class BaseFragment extends BaseSaveFragment implements IBase {
    /**
     * 是否加载完成的标识
     */
    protected boolean createFinish = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() == NO_LAYOUT) {
            return null;
        }
        return View.inflate(getContext(), layoutId(), null);
    }

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
        initData();
        initView();
    }

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
