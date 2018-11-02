package com.base.library.imgcrop;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.yalantis.ucrop.UCrop;

/**
 * 裁剪过程中的助手类
 *
 * @author xuechao
 * @date 2018/11/1 下午3:45
 * @copyright cpx
 */

public abstract class BaseCropHandler {

    /**
     * 配置裁剪选项
     *
     * @param uCrop
     * @return
     */
    public abstract void configCrop(UCrop uCrop);

    /**
     * 处理裁剪后的结果
     *
     * @param resultUri
     */
    public abstract void cropResult(Uri resultUri);

    /**
     * 处理裁剪出错后的结果
     *
     * @param cropError
     */
    public abstract void cropError(Throwable cropError);


    /**
     * 处理裁剪后的结果
     *
     * @param result
     */
    public final void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        cropResult(resultUri);
    }

    /**
     * 处理裁剪错误
     *
     * @param result
     */
    public final void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        cropError(cropError);
    }
}
