package com.base.router.empty;

import com.base.library.R;
import com.base.project.base.fragment.BaseLazyFragment;

/**
 * Describe:空的fragment
 * <p>
 * Created by zhigang wei
 * on 2018/9/3
 * <p>
 * Company :cpx
 */
public class EmptyFragment extends BaseLazyFragment {

    @Override
    public int layoutId() {
        return R.layout.activity_empty;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }
}
