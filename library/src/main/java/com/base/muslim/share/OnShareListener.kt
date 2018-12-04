package com.base.muslim.share

/**
 * Description:
 * 分享接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
interface OnShareListener {
    fun onShareSuccess(type: String)
    fun onShareFail(type: String, cause: String)
}