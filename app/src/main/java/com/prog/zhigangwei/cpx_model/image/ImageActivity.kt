package com.prog.zhigangwei.cpx_model.image

import com.base.muslim.base.activity.BaseFragmentActivity
import com.base.muslim.camera.OnPicturePathListener
import com.base.muslim.camera.PictureCapture
import com.base.muslim.camera.utils.Photo
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
class ImageActivity : BaseFragmentActivity(), OnPicturePathListener {

    private lateinit var path: String


    override fun setContentViews() {
        setContentView(R.layout.activity_image)
        super.setContentViews()
    }

    override fun initResource() {
        path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535091121218&di=32c9e8ae26cf5b3ac60ab3312a435233&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8718367adab44aedb794e128bf1c8701a08bfb20.jpg"
    }


    override fun initWidget() {
        btn_glide.setOnClickListener {
            Glide.with(this).asBitmap().load(path).into(iv_user)
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
