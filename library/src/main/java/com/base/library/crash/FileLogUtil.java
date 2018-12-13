package com.base.library.crash;

import android.content.Context;

import com.base.library.utils.utilcode.util.TimeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 日志工具，主要记录错误日志，方便在设备上查看
 *
 * 日志地址：/storage/emulated/0/Android/data/包名/files/error/
 *
 * @author wzg
 */
public class FileLogUtil {
    /**
     * 写下日志
     *
     * @param log 日志内容
     */
    public static void log(String log, Context context) {
        String currentTime = TimeUtils.getNowString();
        currentTime = "************" + currentTime + "************\n";
        log = toUtf8(log) + "\n";

        File logFile = checkLogFile(context);
        if (logFile == null)
            return;

        try {
            FileWriter fw = new FileWriter(logFile, true); // 日志以插入而非替换方式写入
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(currentTime);
            bw.write(log);
            bw.close();
            fw.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * 检查日志文件是否存在，没有则创建
     */
    public static File checkLogFile(Context context) {
        File logFile = null;
        String logFileName = TimeUtils.getNowString()+ ".log";
        File folder = new File(context.getExternalFilesDir(null),"error"); // 创建文件夹
        logFile = new File(folder, logFileName); // 创建log文件
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return logFile;
    }

    public static String toUtf8(String str) {
        try {
            return new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
