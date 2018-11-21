package com.base.muslim.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.base.library.R;
import com.base.library.rxlifecycle.components.support.RxFragment;

import java.util.List;


/**
 * 基础类管理类
 * 系统方法
 * Created by WZG on 2016/1/28.
 */
public class BaseFragmentManagerFragment extends RxFragment {
    protected FragmentManager fragmentManager;
    /**当前显示的位置*/
    protected int show = 0;
    /**tab页*/
    private List<BaseFragment> fragmentList;
    /**布局*/
    private int layout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getChildFragmentManager();
    }

    /**
     * 设置当前tab里显示的fragment页面
     *
     * @param fragment
     */
    protected void setFragment(int layout, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fade_out);
        ft.replace(layout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


    /**
     * 初始化fragment显示界面
     *
     * @param layout
     * @param fragmentList
     */
    protected void initFragment(int layout, List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        this.layout = layout;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fade_out);
        for (BaseFragment fragment : fragmentList) {
            transaction.add(layout, fragment).hide(fragment);
        }
        transaction.show(fragmentList.get(show)).commit();
    }

    /**
     * 指定显示的fragment位置
     *
     * @param index
     */
    protected void showFragment(int index) {
        if (show != index) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragmentList.get(show));
            BaseFragment fragment = fragmentList.get(index);
            if (fragment.isAdded()) {
                transaction.show(fragment).commit();
            } else {
                transaction.add(layout, fragment).show(fragment).commit();
            }
            show = index;
        }
    }
}
