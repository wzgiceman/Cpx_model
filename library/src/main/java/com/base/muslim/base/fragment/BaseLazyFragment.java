package com.base.muslim.base.fragment;


import android.os.Bundle;
import android.view.View;


/**
 * 懒加载的fragment
 * 使用方法：
 * 1.实现{@link #lazyLoadData()}方法,在该方法中调用懒加载数据的方法，
 * 2.在懒加载数据请求成功后调用{@link #lazyLoadDataCompleted()}
 *
 * @author xuechao
 * @date 2018/9/26 上午9:32
 * @copyright cpx
 */

public abstract class BaseLazyFragment extends BaseSaveFragment {
    /**
     * 是否加载数据
     */
    private boolean hasLoadData = false;
    /**
     * 判断view是否创建
     */
    private boolean isViewCreated = false;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        isViewCreated = true;
        if (savedInstanceState == null) {
            initFragment();
        }
    }


    /**
     * 初始化fragment的根方法
     */
    protected void initFragment() {
        initResource();
        loadData();
        initWidget();
    }


    /**
     * 初始化数据
     */
    protected abstract void initResource();

    /**
     * 懒加载数据
     */
    protected abstract void lazyLoadData();

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //如果view没有创建或者不可见，则不能加载数据
        if (!isViewCreated || !isVisibleToUser) {
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
        if (this.getUserVisibleHint() && !hasLoadData) {
            lazyLoadData();
        }
    }


    /**
     * 数据懒加载完成调用该方法
     */
    protected void lazyLoadDataCompleted() {
        hasLoadData = true;
    }
}
