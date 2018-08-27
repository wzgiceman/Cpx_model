package com.base.router;

import com.base.library.utils.AbLogUtil;
import com.base.muslim.base.fragment.BaseFragment;

import javax.annotation.Nullable;

/**
 * Describe:路由器
 * <p>
 * <p>
 * Created by zhigang wei
 * on 2018/4/18
 * <p>
 * <p>
 * Company :cpx
 */
public class FragmentRouter {

    public static @Nullable
    BaseFragment getFragment(String name) {
        BaseFragment fragment = null;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (BaseFragment) fragmentClass.newInstance();
        } catch (Exception e) {
            AbLogUtil.e("The fragment cannot be found");
        }
        return fragment;
    }
}
