package com.base.muslim.base.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.base.library.R;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;
import com.base.library.utils.utilcode.util.FragmentUtils;
import com.base.muslim.base.fragment.BaseFragment;

import java.util.List;


/**
 * Fragment基础管理类
 * 系统方法
 * Created by WZG on 2016/1/28.
 */
public class BaseFragmentManagerActivity extends RxAppCompatActivity {
    protected FragmentManager fragmentManager;
    /**当前显示的位置*/
    protected int show = 0;
    /**tab页*/
    private List<BaseFragment> fragmentList;
    /**布局*/
    private int layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * 设置当前tab里显示的fragment页面
     *
     * @param fragment
     */
    protected void setFragment(int layout, Fragment fragment) {
        FragmentUtils.add(fragmentManager, fragment, layout, R.anim.fragment_enter, R.anim.fade_out);
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
    protected BaseFragment initFragment(int layout, List<BaseFragment> fragmentList) {
        this.fragmentList = fragmentList;
        this.layout = layout;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fade_out);
        for (BaseFragment fragment : fragmentList) {
            transaction.add(layout, fragment).hide(fragment);
        }
        BaseFragment fragment = fragmentList.get(show);
        transaction.show(fragment).commit();
        return fragment;
    }

    /**
     * 指定显示的fragment位置
     *
     * @param index
     */
    protected BaseFragment showFragment(int index) {
        if (fragmentList == null || fragmentList.size() == 0 || index >= fragmentList.size()) return null;
        BaseFragment fragment = fragmentList.get(index);
        if (show != index) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(fragmentList.get(show));
            if (fragment.isAdded()) {
                transaction.show(fragment).commit();
            } else {
                transaction.add(layout, fragment).show(fragment).commit();
            }
            show = index;
        }
        return fragment;
    }

}
