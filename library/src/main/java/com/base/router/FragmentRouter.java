package com.base.router;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.base.library.utils.AbLogUtil;
import com.base.muslim.base.fragment.BaseFragment;
import com.base.router.empty.EmptyFragment;


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

    @NonNull
    public static BaseFragment getFragment(String name) {
        BaseFragment fragment = null;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (BaseFragment) fragmentClass.newInstance();
        } catch (Exception e) {
            fragment = new EmptyFragment();
            AbLogUtil.e("The fragment cannot be found");
        }
        return fragment;
    }
}
