package com.base.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Describe:recyclerview嵌入的GridView
 * <p>
 * Created by zhigang wei
 * on 2017/6/2
 * <p>
 * Company :Sichuan Ziyan
 */
public class RcGridView extends GridView {

    public RcGridView(Context context) {
        super(context);
    }

    public RcGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RcGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}