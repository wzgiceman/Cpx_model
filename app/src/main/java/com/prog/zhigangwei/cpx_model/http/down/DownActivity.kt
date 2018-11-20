package com.prog.zhigangwei.cpx_model.http.down

import com.base.library.rxRetrofit.downlaod.DownInfo
import com.base.library.rxRetrofit.downlaod.HttpDownManager
import com.base.library.rxRetrofit.listener.HttpDownOnNextListener
import com.base.library.rxRetrofit.utils.DownDbUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.muslim.base.extension.showToast
import com.prog.zhigangwei.cpx_model.R
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
class DownActivity : BaseFragmentActivity() {
    override fun layoutId() = R.layout.activity_http_down

    private var info: DownInfo? = DownDbUtil.getInstance().queryDownBy(10001)

    override fun initData() {
        /*默认会存储在数据库中,所以需要读取历史纪录可以通过key:id获取*/
        if (null == info) {
            info = DownInfo()
            info!!.id = 10001
            info!!.url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
            info!!.isUpdateProgress = true
            info!!.savePath = File(getExternalFilesDir(null), System.currentTimeMillis().toString() + ".mp4").absolutePath
            DownDbUtil.getInstance().save(info!!)
        }
        /*显示上一次下载的位置*/
        tv_progress.text = "${info!!.readLength}/${info!!.countLength}"

        info!!.listener = object : HttpDownOnNextListener<DownInfo>() {

            override fun onNext(t: DownInfo?) {
                showToast("下载成功路径:${t!!.savePath}")
            }

            override fun onStart() {
                showToast("onStart..........")
            }

            override fun onComplete() {
                showToast("onComplete.........")
            }

            override fun updateProgress(readLength: Long, countLength: Long) {
                tv_progress.text = "${readLength}/${countLength}"
            }
        }

    }

    override fun initView() {
        btn_down.setOnClickListener {
            HttpDownManager.getInstance().startDown(info!!)
        }

        btn_pause.setOnClickListener {
            HttpDownManager.getInstance().pause(info!!)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        HttpDownManager.getInstance().remove(info!!)
    }

}