package com.base.library.rxRetrofit.httpList.httpList

import com.base.library.rxRetrofit.exception.ApiException

/**
 * Description:
 * 链式调用Api结果接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/17
 */
interface HttpListOnNextListener {

    /**
     * 调用成功返回数据
     * @param listResult 链式调用Api和回调结果类[ListApiResult]
     */
    fun onListNext(listResult: ListApiResult)

    /**
     * 调用失败
     * @param e 错误类型
     * @param listResult 链式调用Api和回调结果类[ListApiResult]
     */
    fun onListError(e: ApiException,listResult: ListApiResult)
}