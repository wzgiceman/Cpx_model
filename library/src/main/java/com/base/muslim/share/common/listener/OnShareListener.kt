package com.base.muslim.share.common.listener

/**
 * Description:
 * 分享接口
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/12/4
 */
interface OnShareListener {
    /**
     * 分享成功
     * @param type 分享方式
     */
    fun onShareSuccess(type: String)
    /**
     * 分享失败
     * @param type 分享方式
     * @param cause 失败原因
     */
    fun onShareFail(type: String, cause: String)
}