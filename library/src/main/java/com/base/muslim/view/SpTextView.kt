package com.base.muslim.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.base.share_data.ShareSparse
import com.base.share_data.user.User

/**
 * Describe:古兰经设置字体
 *
 *
 * Created by zhigang wei
 * on 2018/6/29
 *
 *
 * Company :cpx
 */
@SuppressLint("AppCompatCustomView")
class SpTextView : TextView {
    val user: User by lazy { ShareSparse.getValueBy(ShareSparse.USER_CLS) as User }

    constructor(context: Context) : super(context) {
        initTtf()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initTtf()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTtf()
    }

    fun initTtf() {
        //得到AssetManager
        try {
            val mgr = context.assets
            //根据路径得到Typeface
            val tf = Typeface.createFromAsset(mgr, "fonts/" + user.spFontTtf)
            //设置字体
            typeface = tf
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
