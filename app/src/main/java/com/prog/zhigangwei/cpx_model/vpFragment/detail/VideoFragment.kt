package com.prog.zhigangwei.cpx_model.vpFragment.detail

import android.support.v7.widget.GridLayoutManager
import com.alibaba.fastjson.JSONObject
import com.base.library.easyrecyclerview.decoration.SpaceDecoration
import com.base.library.rxRetrofit.exception.ApiException
import com.base.library.rxRetrofit.http.HttpManager
import com.base.library.rxRetrofit.listener.HttpOnNextListener
import com.base.library.utils.utilcode.util.ConvertUtils
import com.base.project.base.fragment.BaseLazyFragment
import com.prog.zhigangwei.cpx_model.R
import com.prog.zhigangwei.cpx_model.vpFragment.common.api.WallDetailApi
import com.prog.zhigangwei.cpx_model.vpFragment.common.bean.Video
import kotlinx.android.synthetic.main.fragment_wall.*


/**
 *
 *Describe:视频详情
 *
 *Created by zhigang wei
 *on 2018/12/4
 *
 *Company :cpx
 */
class VideoFragment : BaseLazyFragment(), HttpOnNextListener {
    private val adapter by lazy { VideoAdapter(context) }
    private val httpManager by lazy { HttpManager(this, this) }
    override fun layoutId() = R.layout.fragment_wall

    override fun initData() {
        erc.setRefreshing(true)
        httpManager.doHttpDeal(WallDetailApi(arguments.getString("key")))
    }

    override fun initView() {
        erc.setLayoutManager(GridLayoutManager(context, 3))
        erc.adapter = adapter
        val itemDecoration = SpaceDecoration(ConvertUtils.dp2px(8.0f))//参数是距离宽度
        itemDecoration.setPaddingEdgeSide(true)//是否为左右2边添加padding.默认true.
        itemDecoration.setPaddingStart(true)//是否在给第一行的item添加上padding(不包含header).默认true.
        itemDecoration.setPaddingHeaderFooter(false)//是否对Header于Footer有效,默认false.
        erc.setItemDecoration(itemDecoration)

        /*下拉刷新*/
        erc.setRefreshListener {
            initData()
        }
        erc.errorView.setOnClickListener {
            initData()
        }
        erc.emptyView.setOnClickListener {
            initData()
        }
    }

    override fun onNext(result: String, method: String) {
        erc.setRefreshing(false)
        adapter.removeAll()
        val video = JSONObject.parseObject(result, Video::class.java)
        if (video?.entries == null || video.entries.isEmpty()) {
            erc.showEmpty()
        } else {
            adapter.addAll(JSONObject.parseObject(result, Video::class.java).entries)
        }
        loadingSuccess()
    }

    override fun onError(e: ApiException, method: String) {
        loadingFail()
        erc.showError()
        erc.setRefreshing(false)
    }

}