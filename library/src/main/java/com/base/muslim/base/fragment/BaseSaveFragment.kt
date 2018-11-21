package com.base.muslim.base.fragment

import android.os.Bundle

/**
 * Describe:需要记录保存状态,为了手动恢复数据
 *
 *
 * Created by zhigang wei
 * on 2017/6/2
 *
 *
 * Company :Sichuan Ziyan
 */
open class BaseSaveFragment : BaseToolFragment() {

    protected var bundle: Bundle? = null

    init {
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Restore State Here
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched()
        }
    }

    /**
     * Called when the fragment is launched for the first time.
     * In the other words, fragment is now recreated.
     */
    protected fun onFirstTimeLaunched() {

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // Save State Here
        saveStateToArguments()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Save State Here
        saveStateToArguments()
    }

    ////////////////////
    // Don't Touch !!存入的值用来校验是否执行过onSaveInstanceState
    ////////////////////

    private fun saveStateToArguments() {
        if (view != null)
            bundle = saveState()
        if (bundle != null) {
            val b = arguments
            b?.putBundle("internalSavedViewState8954201239547", bundle)
        }
    }

    ////////////////////
    // Don't Touch !!校验是否执行过onSaveInstanceState
    ////////////////////

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (b != null) {
            bundle = b.getBundle("internalSavedViewState8954201239547")
            if (bundle != null) {
                restoreState()
                return true
            }
        }
        return false
    }

    /////////////////////////////////
    // Restore Instance State Here
    /////////////////////////////////

    private fun restoreState() {
        val bundle = this.bundle ?: return
        // For Example
        //tv1.setText(bundle.getString("text"));
        onRestoreState(bundle)
    }


    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  This is called after [.onCreateView]
     * and before [.onViewStateRestored].
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */

    protected fun onRestoreState(savedInstanceState: Bundle) {

    }

    //////////////////////////////
    // Save Instance State Here
    //////////////////////////////

    private fun saveState(): Bundle {
        val state = Bundle()
        // For Example
        //state.putString("text", tv1.getText().toString());
        onSaveState(state)
        return state
    }

    /**
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
    protected fun onSaveState(outState: Bundle) {

    }
}
