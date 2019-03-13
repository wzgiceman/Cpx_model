package com.base.project.base.activity

import android.os.Bundle
import com.base.project.base.IBase
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubePlayer

/**
 * Description:
 * YouTubeActivity 基础类
 * 播放YouTube视频的页面，需要继承此Activity
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2019/2/12
 */
abstract class BaseYouTubeActivity : YouTubeBaseActivity(), IBase, YouTubePlayer.OnInitializedListener {
    /**上个界面传入的数据*/
    protected var bundle: Bundle = Bundle()
    /**控制是否需要绘制UI界面*/
    protected var beforeUi = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveState(savedInstanceState)
        if (beforeUi) {
            initActivity()
        } else {
            initBeforeFailed()
        }
    }

    /**
     * 初始化Activity的根方法
     */
    protected open fun initActivity() {
        if (layoutId() != IBase.NO_LAYOUT) {
            setContentView(layoutId())
        }
        initData()
        initView()
    }

    /**
     * activity不初始化回掉处理
     */
    protected fun initBeforeFailed() {

    }

    /*start------------------------bundle数据的恢复和保存-------------------------------*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle("bundle", bundle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreSaveState(savedInstanceState)
    }

    /**
     * 复原数据
     *
     * @param savedInstanceState
     */
    protected fun restoreSaveState(savedInstanceState: Bundle?) {
        if (null != intent.extras) {
            bundle = intent.extras
        }
        if (bundle == null && savedInstanceState != null && savedInstanceState.containsKey("bundle")) {
            bundle = savedInstanceState.getBundle("bundle")
        }
    }
    /*end------------------------bundle数据的恢复和保存-------------------------------*/


}
