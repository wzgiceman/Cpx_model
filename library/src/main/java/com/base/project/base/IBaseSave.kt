package com.base.project.base

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
     * Fragment第一次创建
     */
    fun onFirstTimeLaunched()

    /**
     * 保存状态
     * @param outState 保存到此bundle中
     */
    fun onSaveState(outState: Bundle)

    /**
     * 恢复状态
     * @param savedInstanceState [onSaveState]中保存了状态的bundle
     */
    fun onRestoreState(savedInstanceState: Bundle)
}