package com.prog.zhigangwei.cpx_model.image

import com.base.project.base.activity.BaseActivity
import com.base.project.camera.OnPicturePathListener
import com.base.project.camera.PictureCapture
import com.base.project.camera.utils.Photo
import com.bumptech.glide.Glide
import com.prog.zhigangwei.cpx_model.R
import kotlinx.android.synthetic.main.activity_image.*

/**
 *
 *Describe:图片处理
 *
 *Created by zhigang wei
 *on 2018/8/24
 *
 *Company :cpx
 */
class ImageActivity : BaseActivity(), OnPicturePathListener {

    private lateinit var path: String
    private lateinit var prePath: String

    override fun layoutId() = R.layout.activity_image

    override fun initData() {
        prePath = "http://img.mp.sohu.com/upload/20170520/1ac53c2182bd4d6dac5abed5dc3cbe43.png"
        path="http://tupian.qqjay.com/u/2017/1201/2_161641_2.jpg"


        /*大图片需要在service中提前加载*/
        Glide.with(this)
                .load(prePath)
                .preload()
    }


    override fun initView() {
        btn_glide.setOnClickListener {
            Glide.with(this).asBitmap().load(path).into(iv_user)
        }


        btn_pre_local.setOnClickListener {
            Glide.with(this)
                    .load(prePath)
                    .into(iv_gif)
        }

        btn_local.setOnClickListener {
            /*PictureCapture提供了很多本地图片都获取方法,点击详情查看*/
            PictureCapture.getPicture(this, this)
        }


    }

    override fun onPhoto(photo: Photo) {
        Glide.with(this@ImageActivity).asBitmap().load(photo.originalFile.absolutePath).into(iv)
    }


}
