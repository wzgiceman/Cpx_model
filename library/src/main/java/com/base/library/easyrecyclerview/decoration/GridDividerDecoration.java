package com.base.library.easyrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


/**
 *
 * Grid的RecyclerView的分割线
 * 只有中间的分割线，没有边框
 *
 * @author xuechao
 * @date 2018/11/9 上午9:41
 * @copyright cpx
 */
public class GridDividerDecoration extends RecyclerView.ItemDecoration {

    private ColorDrawable mColorDrawable;
    private int mWidth;

    public GridDividerDecoration(@ColorInt int color, @Px int width) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mWidth = width;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 画水平方向的分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin + mWidth;
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mWidth;
            mColorDrawable.setBounds(left, top, right, bottom);
            mColorDrawable.draw(canvas);
        }
    }

    /**
     * 画垂直方向的分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mWidth;
            mColorDrawable.setBounds(left, top, right, bottom);
            mColorDrawable.draw(canvas);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int right = mWidth;
        int bottom = mWidth;
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {//如果是最后一行，则不需要画底部
            bottom = 0;
        }
        if (isLastColumn(parent, itemPosition, spanCount, childCount)) {//如果是最后一列，则不需要画边框
            right = 0;
        }
        outRect.set(0, 0, right, bottom);
    }


    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 是否是最后一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int lastRawCount = childCount % spanCount;
        if (lastRawCount == 0) {
            lastRawCount = spanCount;
        }
        if (layoutManager instanceof GridLayoutManager) {
            return pos >= (childCount - lastRawCount);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            //纵向滚动
            if (StaggeredGridLayoutManager.VERTICAL == orientation) {
                return pos >= (childCount - lastRawCount);
            } else if (StaggeredGridLayoutManager.HORIZONTAL == orientation) {//横向滚动
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 是否是最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int lastRawCount = childCount % spanCount;
        if (lastRawCount == 0) {
            lastRawCount = spanCount;
        }
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (StaggeredGridLayoutManager.VERTICAL == orientation) {
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else if (StaggeredGridLayoutManager.HORIZONTAL == orientation) {
                return pos >= (childCount - lastRawCount);
            }
        }
        return false;
    }


}
