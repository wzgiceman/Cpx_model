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
    private ProgressDialog loadingDialog;

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
        if (loadingDialog == null) {
            loadingDialog = ProgressDialog.show(activity, null, message);
            loadingDialog.setCancelable(cancel);
        } else if (!loadingDialog.isShowing()) {
            loadingDialog.setMessage(message);
            loadingDialog.setCancelable(cancel);
            loadingDialog.show();
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
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected RxAppCompatActivity getRxActivity() {
        return (RxAppCompatActivity) getContext();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }
}
