package com.base.muslim.base

import android.os.Bundle

/**
 * Description:
 * 状态保存与恢复接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/23
 */
interface IBaseSave {
    /**
     * 第一次打开
     * Called when the fragment is launched for the first time.
     * In the other words, fragment is now recreated.
     */
    fun onFirstTimeLaunched()

    /**
     * 保存数据
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  This is called after [.onCreateView]
     * and before [.onViewStateRestored].
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    fun onSaveState(outState: Bundle)

    /**
     * 恢复数据
     * Called to ask the fragment to save its current dynamic state, so it
     * can later be reconstructed in a new instance of its process is
     * restarted.  If a new instance of the fragment later needs to be
     * created, the data you place in the Bundle here will be available
     * in the Bundle given to [.onRestoreState].
     *
     *
     * Activity.onSaveInstanceState(Bundle)} and most of the discussion there
     * applies here as well.  Note however: *this method may be called
     * at any time before [.onDestroy]*.  There are many situations
     * where a fragment may be mostly torn down (such as when placed on the
     * back stack with no UI showing), but its state will not be saved until
     * its owning activity actually needs to save its state.
     *
     * @param outState Bundle in which to place your saved state.
     */
    fun onRestoreState(savedInstanceState: Bundle)
}