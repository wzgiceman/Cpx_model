package com.base.muslim.base.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.base.library.R;
import com.base.library.utils.AbAppUtil;
import com.base.library.utils.AbStrUtil;
import com.base.library.utils.FirebaseUtils;


/**
 * Describe:基础类工具类:公共方法
 * <p>
 * Created by zhigang wei
 * on 2017/7/4.
 * <p>
 * Company :Sichuan Ziyan
 */
public class BaseFragmentToolsActivity extends BaseFragmentManagerActivity {
    /**
     * 上个界面传入的数据
     */
    protected Bundle bundle;
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
            loadingDailog = ProgressDialog.show(this, null, message);
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
        if (loadingDailog != null && loadingDailog.isShowing()) {
            loadingDailog.dismiss();
        }
    }

    /**
     * 显示toast提示信息
     *
     * @param content
     */
    protected void showToast(String content) {
        Toast toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示toast提示信息
     *
     * @param content
     */
    protected void showToast(int content) {
        Toast toast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        toast.show();
    }


    /**
     * 跳转到指定的activity
     *
     * @param cls
     */
    protected void jumpActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void jumpActivityFinish(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }

    /**
     * 带参数跳转到指定的activity
     *
     * @param cls
     * @param bundle
     */
    protected void jumpActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void jumpActivityFinish(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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


    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                AbAppUtil.closeSoftInput(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing() && null != loadingDailog) {
            loadingDailog.dismiss();
        }
    }


    /*start------------------------bundle数据的恢复和保存-------------------------------*/

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resotreSaveState(savedInstanceState);
    }


    /**
     * 复原数据
     *
     * @param savedInstanceState
     */
    protected void resotreSaveState(Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        if (bundle == null && savedInstanceState != null && savedInstanceState.containsKey("bundle")) {
            bundle = savedInstanceState.getBundle("bundle");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null == bundle) return;
        outState.putBundle("bundle", bundle);
    }

    /*end------------------------bundle数据的恢复和保存-------------------------------*/


}
