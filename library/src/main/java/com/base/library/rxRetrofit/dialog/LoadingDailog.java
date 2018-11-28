package com.base.library.rxRetrofit.dialog;

import android.content.Context;
import android.widget.TextView;

import com.base.library.R;
import com.base.muslim.base.dialog.BaseDialog;


/**
 * 进度加载框
 *
 * @author WZG
 */
public class LoadingDailog extends BaseDialog {
    private TextView tv;

    public LoadingDailog(Context context) {
        super(context, R.style.progress_dialog, false);
    }


    @Override
    public void initView() {
        super.initView();
        tv = findViewById(R.id.tv_load);
    }

    public void showLoading(String title) {
        if (tv != null) {
            tv.setText(title);
        }
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public int layoutId() {
        return R.layout.view_load_more;
    }
}
