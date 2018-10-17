package com.base.router.empty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.R;
import com.base.muslim.base.fragment.BaseFragment;

/**
 * Describe:空的fragment
 * <p>
 * Created by zhigang wei
 * on 2018/9/3
 * <p>
 * Company :cpx
 */
public class EmptyFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_empty, container, false);
    }

    @Override
    protected void initResource() {

    }

    @Override
    protected void initWidget() {

    }
}
