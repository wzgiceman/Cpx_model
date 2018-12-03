package com.prog.zhigangwei.cpx_model.http.down

import android.content.IntentFilter
import com.base.library.rxRetrofit.downlaod.DownInfo
import com.base.library.rxRetrofit.downlaod.HttpDownManager
import com.base.library.rxRetrofit.listener.HttpDownOnNextListener
import com.base.library.rxRetrofit.utils.DownDbUtil
import com.base.library.utils.utilcode.util.LogUtils
import com.base.library.utils.utilcode.util.NetworkUtils
import com.base.muslim.base.activity.BaseActivity
import com.base.muslim.base.extension.showToast
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.http.down.down.NetworkConnectChangedReceiver
import kotlinx.android.synthetic.main.activity_http_down.*
import java.io.File

/**
 *
 *Describe:http多下载
 *
 *Created by zhigang wei
 *on 2018/11/6
 *
 *Company :cpx
 *
 * * 更复杂用例参考地址:https://github.com/wzgiceman/RxjavaRetrofitDemo-string-master/blob/master/app/src/main/java/com/example/retrofit/activity/DownLaodActivity.java
 */
class DownActivity : BaseActivity() {

    companion object {
        const val ID = 16L
    }

    private lateinit var info: DownInfo
    private var receiver: NetworkConnectChangedReceiver? = null

    override fun layoutId() = R.layout.activity_http_down

    override fun initData() {
        info = DownDbUtil.getInstance().queryDownBy(ID) ?: DownInfo().apply {
            id = ID
            url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
            isUpdateProgress = true
            savePath = File(getExternalFilesDir(null), System.currentTimeMillis().toString() + ".mp4").absolutePath
            DownDbUtil.getInstance().save(this)
        }

        /*显示上一次下载的位置*/
        tv_progress.text = String.format("%d / %d", info.readLength, info.countLength)
        info.listener = object : HttpDownOnNextListener<DownInfo>() {

            override fun onNext(t: DownInfo?) {
                LogUtils.d("DownActivity onNext")
                showToast("下载成功路径:${t?.savePath}")
            }

            override fun onStart() {
                LogUtils.d("DownActivity onStart")
                showToast("onStart..........")
            }

            override fun onComplete() {
                LogUtils.d("DownActivity onComplete")
                showToast("onComplete.........")
            }


            override fun onError(e: Throwable?) {
                LogUtils.d("DownActivity onError")
                showToast("onError.........${e?.message}")
                initWifiChangeListener()
            }

            override fun updateProgress(readLength: Long, countLength: Long) {
                tv_progress.text = String.format("%d / %d", readLength, countLength)
            }
        }

    }

    override fun initView() {
        btn_down.setOnClickListener {
            HttpDownManager.getInstance().startDown(info)
        }

        btn_pause.setOnClickListener {
            HttpDownManager.getInstance().pause(info)
        }

        btn_clear.setOnClickListener {
            DownDbUtil.getInstance().deleteDownInfo(info)
            File(info.savePath).delete()
            finish()
        }
    }


    /**
     * 监听网络变动处理
     */
    private fun initWifiChangeListener() {
        if (NetworkUtils.isConnected()) return
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
        filter.addAction("android.net.wifi.STATE_CHANGE")
        receiver = NetworkConnectChangedReceiver {
            LogUtils.d("NetworkConnectChangedReceiver:$it")
            if (!it) return@NetworkConnectChangedReceiver
            LogUtils.d("---->网络连接可用")
            HttpDownManager.getInstance().startDown(info)
        }
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        HttpDownManager.getInstance().pause(info)
        HttpDownManager.getInstance().remove(info)
        if (null != receiver) {
            unregisterReceiver(receiver)
        }
    }

}
