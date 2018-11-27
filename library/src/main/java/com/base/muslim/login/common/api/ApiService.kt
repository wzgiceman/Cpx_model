package com.base.muslim.login.common.api
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Description:
 * ApiService
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/10/12
 */
interface ApiService {

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("v1/login/verificode")
    fun postVerificationCode(@Query("t") type: String, @Field("account") account: String): Observable<String>

    /**
     * 登录
     */
    @GET("v1/login")
    fun getLogin(@QueryMap fields: Map<String, String>): Observable<String>
}
