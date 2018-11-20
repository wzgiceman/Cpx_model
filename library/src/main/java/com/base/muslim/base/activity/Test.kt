package com.base.muslim.base.activity

import android.app.Activity
import android.os.Bundle
import com.base.muslim.base.extension.showToast

/**
 * Description:
 *
 *
 * @author  Alpinist Wang
 * Company: Mobile CPX
 * Date:    2018/11/20
 */
class Test: Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showToast("haha")
    }
}
