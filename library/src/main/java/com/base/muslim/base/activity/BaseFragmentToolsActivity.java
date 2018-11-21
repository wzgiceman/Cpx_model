package com.base.muslim.base.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.base.library.utils.AbAppUtil;
import com.base.muslim.base.extension.ActivityExtensionKt;


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
        if (v instanceof EditText) {
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
        if (isFinishing() && ActivityExtensionKt.getLoadingDialog(this).isShowing()) {
            ActivityExtensionKt.getLoadingDialog(this).dismiss();
        }
    }


    /*start------------------------bundle数据的恢复和保存-------------------------------*/

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreSaveState(savedInstanceState);
    }

    /**
     * 复原数据
     *
     * @param savedInstanceState
     */
    protected void restoreSaveState(Bundle savedInstanceState) {
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
