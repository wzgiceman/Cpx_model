package com.base.muslim.base.fragment;

import android.app.ProgressDialog;

import com.base.library.R;
import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;
import com.base.library.utils.AbStrUtil;
import com.base.muslim.base.extension.ActivityExtensionKt;


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
     * @param title  显示的标题
     */
    protected void showLoading(boolean cancel, String title) {
        RxAppCompatActivity activity = getRxActivity();
        if (!ActivityExtensionKt.isValidActivity(activity)) {
            return;
        }
        String message = AbStrUtil.isEmpty(title) ? getString(R.string.Loading) : title;
        if (loadingDailog == null) {
            loadingDailog = ProgressDialog.show(activity, null, message);
            loadingDailog.setCancelable(cancel);
        } else if (loadingDailog != null && !loadingDailog.isShowing()) {
            loadingDailog.setMessage(message);
            loadingDailog.setCancelable(cancel);
            loadingDailog.show();
        }
    }

    /**
     * 关闭加载框
     */
    protected void closeLoading() {
        RxAppCompatActivity activity = getRxActivity();
        if (!ActivityExtensionKt.isValidActivity(activity)) {
            return;
        }
        if (loadingDailog != null && loadingDailog.isShowing()) {
            loadingDailog.dismiss();
        }
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
