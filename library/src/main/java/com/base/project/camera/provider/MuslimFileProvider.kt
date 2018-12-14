package com.base.project.camera.provider

import android.support.v4.content.FileProvider

/**
 * 解决在manifest.xml文件中使用多个 FileProvider问题
 */
class MuslimFileProvider : FileProvider()