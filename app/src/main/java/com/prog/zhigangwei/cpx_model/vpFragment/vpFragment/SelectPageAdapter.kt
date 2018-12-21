package com.prog.zhigangwei.cpx_model.vpFragment.vpFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.prog.zhigangwei.cpx_model.vpFragment.detail.VedioFragment

/**
 *
 * Channel 选择 Recommend和 Popular 的ViewPager 适配器
 * @author
 * @date 2018/8/27 上午11:03
 * @copyright cpx
 */
class SelectPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return VedioFragment().apply {
            arguments = Bundle().apply { putString("key", "AA")}
        }
    }

    override fun getCount(): Int {
        return 20
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "AA"
    }

}