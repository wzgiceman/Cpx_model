package com.base.project.camera.utils;

import java.io.File;

/**
 * 获取到的图片
 */
public class Photo {
    /**
     * 未压缩的图片文件
     */
    private File originalFile;

    /**
     *压缩过的图片文件
     */
    private File compressionFile;

    /**
     * 剪裁过的图片文件
     */
    private File cropFile;

    /**
     * 获取图片的方式（拍照 CAMERA、相册ALBUM、剪裁CROP）
     */
    private PictureType pictureType;



    public File getOriginalFile() {
        return originalFile;
    }

    public void setOriginalFile(File originalFile) {
        this.originalFile = originalFile;
    }

    public File getCompressionFile() {
        return compressionFile;
    }

    public void setCompressionFile(File compressionFile) {
        this.compressionFile = compressionFile;
    }

    public File getCropFile() {
        return cropFile;
    }

    public void setCropFile(File cropFile) {
        this.cropFile = cropFile;
    }

    public PictureType getPictureType() {
        return pictureType;
    }

    public void setPictureType(PictureType pictureType) {
        this.pictureType = pictureType;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "originalFile=" + originalFile +
                ", compressionFile=" + compressionFile +
                ", cropFile=" + cropFile +
                ", pictureType=" + pictureType +
                '}';
    }
}
