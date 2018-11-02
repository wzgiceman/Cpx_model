package com.base.library.imgcrop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import com.base.library.utils.AbLogUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * 图片裁剪类
 * <pre>
 * 1.
 * ImageCropHelper helper = new ImageCropHelper(this,400,400);
 * 2.
 * helper.setCropHandler(new BaseCropHandler(){
 *     @Override
 *     public void startCrop(UCrop uCrop){
 *
 *     }
 *
 *     @Override
 *     public void cropResult(Uri resultUri){
 *
 *     }
 *
 *     @Override
 *     public void cropError(Throwable cropError){
 *
 *     }
 * })
 *
 * 3.
 * @Override
 * public void onActivityResult(int requestCode, int resultCode,Intent data){
 *      helper.onActivityResult(requestCode,resultCode,data);
 *      super.onActivityResult(requestCode,resultCode,data);
 * }
 *
 * 4. 开始裁剪
 * helper.crop(sourceUri,destUri);
 *
 *
 * </pre>
 *
 *
 * @author xuechao
 * @date 2018/11/1 下午3:11
 * @copyright cpx
 */
public class ImageCropHelper {
    private static final String TAG = "ImageCropHelper";

    public static final int REQUEST_CROP = UCrop.REQUEST_CROP;

    private Activity mActivity;
    private Fragment mFragment;
    private android.support.v4.app.Fragment mV4Fragment;

    private int cropWidth;
    private int cropHeight;

    private BaseCropHandler handler;


    public ImageCropHelper(Fragment fragment, int cropWidth, int cropHeight) {
        this.mFragment = fragment;
        this.mActivity = fragment.getActivity();
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
    }

    public ImageCropHelper(Activity activity, int cropWidth, int cropHeight) {
        this.mActivity = activity;
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
    }

    public ImageCropHelper(android.support.v4.app.Fragment fragment, int cropWidth, int cropHeight) {
        this.mV4Fragment = fragment;
        this.mActivity = fragment.getActivity();
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
    }


    public void setCropHandler(BaseCropHandler handler) {
        this.handler = handler;
    }

    /**
     * 获取随机的uri
     *
     * @return
     */
    public Uri getRandomUri() {
        String path = this.mActivity.getExternalFilesDir(null) + "/photo/" + "crop-" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(this.mActivity, this.mActivity.getPackageName() + ".provider", new File(path));
        } else {
            return Uri.fromFile(new File(path));
        }
    }


    /**
     * 裁剪
     *
     * @param sourceUri
     * @param destUri
     */
    public void crop(Uri sourceUri, Uri destUri) {
        UCrop uCrop = UCrop.of(sourceUri, destUri);
        basisConfig(uCrop);
        advancedConfig(uCrop);
        if (handler != null) {
            handler.configCrop(uCrop);
        }
        if (mFragment != null) {
            uCrop.start(mActivity, mFragment, REQUEST_CROP);
        } else if (mV4Fragment != null) {
            uCrop.start(mActivity, mV4Fragment, REQUEST_CROP);
        } else {
            uCrop.start(mActivity, REQUEST_CROP);
        }
    }

    /**
     * 基本设置
     *
     * @param uCrop
     * @return
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        //设置图片原始宽高比列一样
        uCrop = uCrop.useSourceImageAspectRatio();
        //动态的设置图片的宽高比 16:9,设置宽高比后不能显示自动调节的按钮
        uCrop = uCrop.withAspectRatio(1, 1);
        uCrop = uCrop.withMaxResultSize(600, 600);
        if (cropWidth != 0 && cropHeight != 0) {
            uCrop = uCrop.withAspectRatio(cropWidth, cropHeight);
            uCrop = uCrop.withMaxResultSize(cropWidth, cropHeight);
        }
        return uCrop;
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {

        UCrop.Options options = new UCrop.Options();
        //设置裁剪出来图片的格式為png
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //设置裁剪图片的质量
        options.setCompressionQuality(100);
        //设置手势,分别对应裁剪功能页面的“缩放”，“旋转”，“裁剪”界面，对应的传入NONE，就表示关闭了其手势操作
        options.setAllowedGestures(UCropActivity.NONE, UCropActivity.NONE, UCropActivity.ALL);

        //设置最大缩放比例
        options.setMaxScaleMultiplier(5);
        //设置是否展示矩形裁剪框
        options.setShowCropFrame(true);
        //设置裁剪框横竖线的宽度
//        options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
//        options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
//        options.setCropGridColumnCount(2);
        //设置横线的数量
//        options.setCropGridRowCount(1);

        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);


        return uCrop.withOptions(options);
    }


    /**
     * 在fragment或者activity的onActivityResult()方法中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        AbLogUtil.d(TAG, "onActivityResult: requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (handler == null) {
            return;
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CROP) {
            handler.handleCropResult(data);
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handler.handleCropError(data);
        }
    }

}
