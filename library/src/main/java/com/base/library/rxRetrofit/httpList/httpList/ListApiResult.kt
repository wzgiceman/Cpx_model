package com.base.library.rxRetrofit.httpList.httpList

import com.base.library.rxRetrofit.api.BaseApi

/**
 * Description:
 * 链式调用Api和回调结果类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/17
 */
abstract class ListApiResult {
    /**是否能取消加载框*/
    var cancel = true
    /**是否显示加载框*/
    var showProgress = true
    /**回调结果 <BaseApi的Method, BaseApi的结果String>*/
    val resultMap: HashMap<String, String> = HashMap()
    /**是否按顺序执行*/
    val order = false
    /*设置刷新*/
    var refresh = false
        set(refresh) {
            field=refresh
            getApiList()?.forEach {
                it.isRefresh = refresh
            }
        }

    /**
     * 获取Api列表
     */
    abstract fun getApiList(): Array<BaseApi>?

    /**
     * 将列表结果做转换
     */
    abstract fun convert()

    /**
     * 完成回掉
     */
    open fun complete() {

    }

}