package com.base.muslim.base.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.muslim.base.IBase;


/**
 * 懒加载的fragment
 * 使用方法：
 * 1.实现{@link #lazyLoadData()}方法,在该方法中调用懒加载数据的方法，
 * 2.在懒加载数据请求成功后调用{@link #resetLoadingStatus()}
 *
 * @author xuechao
 * @date 2018/9/26 上午9:32
 * @copyright cpx
 */

public abstract class BaseLazyFragment extends BaseSaveFragment implements IBase {
    /**
     * 是否加载数据
     */
    private boolean loading = false;
    /**
     * 判断view是否创建
     */
    private boolean viewCreated = false;

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
        viewCreated = true;
        if (savedInstanceState == null) {
            initFragment();
        }
    }


    /**
     * 初始化fragment的根方法
     */
    protected void initFragment() {
        initData();
        loadData();
        initView();
    }

    /**
     * 懒加载数据
     */
    protected abstract void lazyLoadData();


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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //如果view没有创建或者不可见，则不能加载数据
        if (!viewCreated || !isVisibleToUser) {
            return;
        }
        loadData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            loadData();
        }
    }

    /**
     * 加载数据
     */
    private void loadData() {
        //如果可见,并且没有加载数据
        if (this.getUserVisibleHint() && !loading) {
            loading = true;
            lazyLoadData();
        }
    }


    /**
     * 重置加载状态
     * 数据懒加载取消/失败调用此方法，使得下次到达此页面时可以再次触发lazyLoadData刷新数据
     */
    protected void resetLoadingStatus() {
        loading = false;
    }
}
