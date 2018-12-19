package com.base.project.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.base.library.R;

/**
 *
 * 密码切换显示密文和明文的EditText
 *
 * @author xuechao
 * @date 2018/11/12 下午3:26
 * @copyright cpx
 */
public class PasswordEditText extends AppCompatEditText {

    /**
     * 显示的图片
     */
    private Drawable mShowDrawable;

    /**
     * 隐藏的图片
     */
    private Drawable mHiddenDrawable;

    /**
     * 密码是否可见
     */
    private boolean isShowPassword;

    /**
     * 删除图片的宽
     */
    private int mDrawableWidth;
    /**
     * 删除图片的高
     */
    private int mDrawableHeight;


    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs, defStyle);
        init();
    }

    /**
     * 解析属性
     */
    public void parseAttributes(Context context, AttributeSet attrs, int defStyle) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyle, 0);
        mShowDrawable = array.getDrawable(R.styleable.PasswordEditText_showPasswordSrc);
        if (mShowDrawable == null) {
            //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
            mShowDrawable = getCompoundDrawables()[2];
        }
        mHiddenDrawable = array.getDrawable(R.styleable.PasswordEditText_hiddenPasswordSrc);
        if (mHiddenDrawable == null) {
            mHiddenDrawable = mShowDrawable;
        }
        mDrawableWidth = array.getDimensionPixelSize(R.styleable.PasswordEditText_passwordSrcWidth, 30);
        mDrawableHeight = array.getDimensionPixelSize(R.styleable.PasswordEditText_passwordSrcHeight, 30);
        isShowPassword = array.getBoolean(R.styleable.PasswordEditText_isShowPassword, false);
        array.recycle();
    }


    private void init() {
        /**
         * setBounds(x,y,width,height);
         * x:组件在容器X轴上的起点
         * y:组件在容器Y轴上的起点
         * width:组件的长度
         * height:组件的
         */
        if (mShowDrawable != null) {
            mShowDrawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        if (mHiddenDrawable != null) {
            mHiddenDrawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
        }
        //默认设置隐藏图标
        setPasswordVisible(isShowPassword);
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
                    setPasswordVisible(!isShowPassword);
                }
            }
        }

        return super.onTouchEvent(event);
    }


    /**
     *
     * 设置密码可见和不可见的图标，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setPasswordVisible(boolean visible) {
        this.isShowPassword = visible;
        Drawable right = visible ? mShowDrawable : mHiddenDrawable;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
        if (isShowPassword) {
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mShowDrawable != null) {
            mShowDrawable.setVisible(visibility == VISIBLE, false);
        }

        if (mHiddenDrawable != null) {
            mHiddenDrawable.setVisible(visibility == VISIBLE, false);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mShowDrawable != null) {
            mShowDrawable.setVisible(getVisibility() == VISIBLE, false);
        }

        if (mHiddenDrawable != null) {
            mHiddenDrawable.setVisible(getVisibility() == VISIBLE, false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Only do this for old apps pre-Nougat; new apps use onVisibilityAggregated
        if (mShowDrawable != null) {
            mShowDrawable.setVisible(false, false);
        }

        if (mHiddenDrawable != null) {
            mHiddenDrawable.setVisible(false, false);
        }
    }
}
