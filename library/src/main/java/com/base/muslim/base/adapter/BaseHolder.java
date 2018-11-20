package com.base.muslim.base.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.easyrecyclerview.adapter.BaseViewHolder;


/**
 * holder基础类
 * Created by WZG on 2016/8/4.
 */
public class BaseHolder<T> extends BaseViewHolder<T> {
    public BaseHolder(View itemView) {
        super(itemView);
    }

    public BaseHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
    }
}
