package com.base.library.swipeBack;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.View;

import java.util.Stack;

/**
 * Created by wzg on 2015-11-26.
 */
public class SwipeBackActivityHelper {
    private static final Stack<SwipeBackActivityHelper> sActivities = new Stack<>();

    public Activity getActivity() {
        return mActivity;
    }

    private Activity mActivity;

    private SwipeBackLayout mParallaxBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
        mParallaxBackLayout = new SwipeBackLayout(mActivity);
        sActivities.push(this);
    }

    public boolean hasSecondActivity() {
        return sActivities.size() >= 2;
    }

    public void onPostCreate() {
        mParallaxBackLayout.attachToActivity(this);
    }

    public void onActivityDestroy() {
        sActivities.remove(this);
    }

    public SwipeBackActivityHelper getSecondActivity() {
        if (sActivities.size() >= 2)
            return sActivities.elementAt(sActivities.size() - 2);
        return null;
    }

    public void drawThumb(Canvas canvas) {
        View decorChild = getBackLayout().getContentView();
        decorChild.draw(canvas);
    }

    public View findViewById(int id) {
        if (mParallaxBackLayout != null) {
            return mParallaxBackLayout.findViewById(id);
        }
        return null;
    }

    public void scrollToFinishActivity() {
        getBackLayout().scrollToFinishActivity();
    }

    public void setBackEnable(boolean enable) {
        getBackLayout().setEnableGesture(enable);
    }

    public SwipeBackLayout getBackLayout() {
        return mParallaxBackLayout;
    }
}
