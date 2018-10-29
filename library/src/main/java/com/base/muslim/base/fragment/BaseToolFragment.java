package com.base.muslim.base.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.base.library.R;
import com.base.library.utils.AbStrUtil;
import com.base.library.utils.FirebaseUtils;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * fragment基础工具类
 * Created by WZG on 2016/1/28.
 */
public class BaseToolFragment extends BaseFragmentManagerFragment {
    /**
     * 加载动画
     */
    private ProgressDialog loadingDailog;

    /**
     * 显示统一的加载框
     *
     * @param cancel 是否可以取消
     * @param title 显示的标题
     */
    protected void showLoading(boolean cancel, String title) {
        String message = AbStrUtil.isEmpty(title) ? getString(R.string.Loading) : title;
        if (loadingDailog == null) {
            loadingDailog = ProgressDialog.show(getRxActivity(), null, message);
            loadingDailog.setCancelable(cancel);
        } else if (loadingDailog != null && !loadingDailog.isShowing()) {
            loadingDailog.setMessage(message);
            loadingDailog.setCancelable(cancel);
            loadingDailog.show();
        }
    }

    protected void closeLoading() {
        if (loadingDailog != null && loadingDailog.isShowing()) {
            loadingDailog.dismiss();
        }
    }


    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showToast(int msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示基本信息
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转到指定的activity
     *
     * @param cls
     */
    public void jumpActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    /**
     * 带参数跳转到指定的activity
     *
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
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
        FirebaseUtils.getInstance().report(key);
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
        FirebaseUtils.getInstance().report(key, bundle);
    }

    protected RxAppCompatActivity getRxActivity() {
        return (RxAppCompatActivity) getContext();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != loadingDailog) {
            loadingDailog.dismiss();
        }

    }

}
