package com.base.clawin.base.mvp

/**
 * Description:
 * P层接口基类
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/10/9
 */
interface IBasePresenter<in V : IBaseView> {

    /**
     * 绑定V层
     */
    fun attachView(mRootView: V)

    /**
     * 解除V层绑定
     */
    fun detachView()

}
