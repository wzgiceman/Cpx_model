package com.base.muslim.user.common.api.login

import com.base.library.retrofit_rx.Api.BaseApi
import com.base.library.utils.AbStrUtil
import com.base.muslim.user.common.api.UserService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import io.reactivex.Observable
import java.io.File
import java.util.*


/**
 *
 *Describe:修改用户信息
 *
 *Created by zhigang wei
 *on 2018/5/8
 *
 *Company :cpx
 */
class ModifyUserApi : BaseApi() {


    var username: String? = null
    var avatar: String? = null



    override fun getObservable(): Observable<*> {
        val httpService = retrofit.create(UserService::class.java)

        val ltPart = ArrayList<MultipartBody.Part>()
        if(!AbStrUtil.isEmpty(username)){
            ltPart.add(getRq("username",username!!))
        }
        if(!AbStrUtil.isEmpty(avatar)){
            val file = File(avatar)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile)
            ltPart.add(body)
        }
        return httpService.modifyMine(ltPart)
    }


    private fun getRq(key: String, value: String): MultipartBody.Part {
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), value)
        return MultipartBody.Part.createFormData(key, null, requestBody)
    }

}
