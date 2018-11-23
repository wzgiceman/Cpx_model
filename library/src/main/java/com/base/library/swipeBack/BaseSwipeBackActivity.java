package com.base.library.swipeBack;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.base.library.rxlifecycle.components.support.RxAppCompatActivity;
/**
 * Describe:仿微信滑动销毁
 * <p>
 * Created by zhigang wei
 * on 2017/7/19.
 * <p>
 * Company :Sichuan Ziyan
 */
public abstract class BaseSwipeBackActivity extends RxAppCompatActivity {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    @NonNull
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.onActivityDestroy();
    }

    public SwipeBackLayout getBackLayout() {
        return mHelper.getBackLayout();
    }

    public void setBackEnable(boolean enable) {
        mHelper.setBackEnable(enable);
    }

    public void scrollToFinishActivity() {
        mHelper.scrollToFinishActivity();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mHelper = new SwipeBackActivityHelper(this);
    }
}
