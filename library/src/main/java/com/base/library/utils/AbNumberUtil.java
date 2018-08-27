package com.base.library.utils;

/**
 * Describe:数字类
 * <p>
 * Created by zhigang wei
 * on 2018/7/20
 * <p>
 * Company :cpx
 */
public class AbNumberUtil {

    /**
     * 替换阿拉语言
     *
     * @param number
     * @return
     */
    public static String convertToNumber(String number) {
        try {
            return number.replace("0", "٠").replace("1", "١")
                    .replace("2", "٢").replace("3", "٣")
                    .replace("4", "٤").replace("5", "٥")
                    .replace("6", "٦").replace("7", "٧")
                    .replace("8", "٨").replace("9", "٩");
        } catch (Exception e) {
            return number;
        }
    }

}
