package com.base.muslim.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.base.library.utils.AbNumberUtil
import java.util.*

/**
 * Describe:阿拉伯下字体设置
 *
 *
 * Created by zhigang wei
 * on 2018/6/29
 *
 *
 * Company :cpx
 */
@SuppressLint("AppCompatCustomView")
class ArNumberTextView : TextView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val textMsg = if (Locale.getDefault().language.contains("ar")) AbNumberUtil.convertToNumber(text.toString()) else text
        super.setText(textMsg ?: "", type)
    }


}
