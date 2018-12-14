package com.base.project.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.base.library.R;

/**
 *
 * 带删除的EditText
 *
 * @author xuechao
 * @date 2018/11/12 下午3:26
 * @copyright cpx
 */
public class ClearEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener {


    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;

    /**
     * 删除图片的宽
     */
    private int mDrawableWidth;
    /**
     * 删除图片的高
     */
    private int mDrawableHeight;


    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs, defStyle);
        init();
    }

    /**
     * 解析属性
     */
    public void parseAttributes(Context context, AttributeSet attrs, int defStyle) {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText, defStyle, 0);
        mClearDrawable = array.getDrawable(R.styleable.ClearEditText_clearSrc);
        if (mClearDrawable == null) {
            mClearDrawable = getCompoundDrawables()[2];
        }
        mDrawableWidth = array.getDimensionPixelSize(R.styleable.ClearEditText_clearSrcWidth, 30);
        mDrawableHeight = array.getDimensionPixelSize(R.styleable.ClearEditText_clearSrcHeight, 30);
        array.recycle();
    }

    private void init() {

        /**
         * setBounds(x,y,width,height);
         * x:组件在容器X轴上的起点
         * y:组件在容器Y轴上的起点
         * width:组件的长度
         * height:组件的高度
         */
        if (mClearDrawable != null) {
            mClearDrawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mClearDrawable != null) {
            mClearDrawable.setVisible(visibility == VISIBLE, false);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mClearDrawable != null) {
            mClearDrawable.setVisible(getVisibility() == VISIBLE, false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mClearDrawable != null) {
            mClearDrawable.setVisible(false, false);
        }
    }


}
