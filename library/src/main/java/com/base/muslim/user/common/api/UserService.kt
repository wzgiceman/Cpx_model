package com.base.muslim.user.common.api

import com.base.muslim.user.common.api.login.*
import com.base.muslim.user.common.api.source.NoticePostApi
import okhttp3.MultipartBody
import retrofit2.http.*
import io.reactivex.Observable


/**
 *
 *Describe:接口
 *
 *Created by zhigang wei
 *on 2018/5/11
 *
 *Company :cpx
 */
interface UserService {

    /**
     *注册
     */
    @POST("users/")
    fun signUp(@Body signUpApi: SignUpApi): Observable<String>

    /**
     * 获取验证码
     */
    @POST("users/send_check_code")
    fun sendCode(@Body api: SendCodeApi): Observable<String>

    /**
     * 设置新密码
     */
    @POST("users/forget_password")
    fun setPsd(@Body api: SetPsdApi): Observable<String>

    /**
     * 登录
     */
    @POST("users/login")
    fun login(@Body api: LoginApi): Observable<String>


    /**
     * facebook登录
     */
    @POST("users/create_facebook_user")
    fun loginFaceBook(@Body api: LoginFaceBookApi): Observable<String>

    /**
     * facebook绑定
     */
    @POST("users/bind_facebook_user")
    fun bindFaceBook(@Body api: BindFaceBookApi): Observable<String>

    /**
     * 修改个人资料
     *
     * @param parts
     * @return
     */
    @Multipart
    @PUT("user")
    fun modifyMine(@Part parts: List<MultipartBody.Part>): Observable<String>

    /**
     * 发送通知需要的tokean信息到服务器
     */
    @POST("message/message/userTerminal")
    fun postNotice(@Body notic: NoticePostApi): Observable<String>

}