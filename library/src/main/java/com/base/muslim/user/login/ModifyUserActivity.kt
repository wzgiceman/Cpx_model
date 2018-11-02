package com.base.muslim.user.login

import android.view.View
import com.base.library.retrofit_rx.exception.ApiException
import com.base.library.retrofit_rx.http.HttpManager
import com.base.library.retrofit_rx.listener.HttpOnNextListener
import com.base.library.utils.AbStrUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.muslim.camera.OnPicturePathListener
import com.base.muslim.camera.PictureCapture
import com.base.muslim.camera.utils.Photo
import com.base.muslim.tipsdialog.TipsDialog
import com.base.share_data.ShareSparse
import com.base.share_data.share_msg.ShareDataDb
import com.base.share_data.user.User
import com.bumptech.glide.Glide
import com.base.library.R
import com.base.muslim.user.common.api.login.ModifyUserApi
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_modify_user.*
import kotlinx.android.synthetic.main.include_head_title.*


/**
 *
 *Describe:修改个人资料
 *
 *Created by zhigang wei
 *on 2018/6/14
 *
 *Company :cpx
 */
class ModifyUserActivity : BaseFragmentActivity(), HttpOnNextListener, OnPicturePathListener {
    private val httpManager by lazy { HttpManager(this, this) }
    private val user: User by lazy { ShareSparse.getValueBy(ShareSparse.USER_CLS) as User }
    private val modifyUserApi by lazy { ModifyUserApi() }
    private var userPhtoChange = false

    override fun setContentViews() {
        setContentView(R.layout.activity_modify_user)
        super.setContentViews()
    }

    override fun initResource() {

    }

    override fun initWidget() {
        tv_back.setOnClickListener { onBackPressed() }
        tv_back.text = getString(R.string.person_infor)
        Glide.with(this).asBitmap().load(user.avatar).apply(RequestOptions().placeholder(R.mipmap.user_default_photo)).into(iv_user)
        etv_email.setText(user.userName)
        etv_email.setSelection(user.userName.length)

        iv_user.setOnClickListener { PictureCapture.getCompressionPicture(this, this, false) }

        tv_login.setOnClickListener {

            val tipDilog = TipsDialog(this, object : TipsDialog.OnDropBtnClickListener {
                override fun onClick(v: View, type: Any) {
                    user.isLogin = false
                    user.token = ""
                    user.avatar=""
                    ShareDataDb.getInstance().savrOrUpdate(user)
                    finish()
                }
            })
            tipDilog.showDialog(null, this.getString(R.string.log_out_deter))
        }
    }


    override fun onBackPressed() {
        val nikeName = etv_email.text.toString().trim()
        if (!AbStrUtil.isEmpty(nikeName) && nikeName != user.userName.trim()) {
            modifyUserApi.username = nikeName
            httpManager.doHttpDeal(modifyUserApi)
            return
        }
        if (userPhtoChange) {
            httpManager.doHttpDeal(modifyUserApi)
            return
        }
        super.onBackPressed()
    }


    override fun onNext(resulte: String, method: String) {
        user.setLoginUser(resulte)
        ShareDataDb.getInstance().savrOrUpdate(user)
        showToast(R.string.modify_user_suc)
        finish()
    }

    override fun onError(e: ApiException, method: String) {
        showToast(e.displayMessage)
    }


    override fun onPhoto(photo: Photo) {
        userPhtoChange = true
        modifyUserApi.avatar = photo.compressionFile.absolutePath
        Glide.with(this).asBitmap().load(modifyUserApi.avatar).into(iv_user)
    }

}