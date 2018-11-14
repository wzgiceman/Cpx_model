package com.base.library.rxRetrofit.exception;

/**
 * 运行时自定义错误信息
 * 自由添加错误，需要自己扩展
 * Created by WZG on 2016/7/16.
 */
public class HttpTimeException extends RuntimeException {
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 0x1002;
    /*本地无缓存错误*/
    public static final int NO_CACHE_ERROR = 0x1003;
    /*缓存过时错误*/
    public static final int CACHE_TIMEOUT_ERROR = 0x1004;
    /*断点下载错误*/
    public static final int CACHE_DOWN_ERROR = 0x1005;
    /*服务器down了,无返回数据*/
    public static final int CACHE_HTTP_ERROR = 0x1006;
    /*tokean过期，需要重新登录*/
    public static final int CACHE_TOKEAN_ERROR = 0x1007;
    /*http请求返回失败*/
    public static final int CACHE_HTTP_POST_ERROR = 0x1008;
    /*用户在其他设备登录*/
    public static final int CACHE_MORE_LOGIN_ERROR = 0x1009;
    /*交互取消*/
    public static final int HTTP_CANCEL = 0x1010;
    /*交互取消*/
    public static final int HTTP_DATA_NULL = 0x1011;
    /*获取tokean失败*/
    public static final int HTTP_TOKEN_ERROR = 0x10112;
    /*CONFIG失败*/
    public static final int HTTP_CONFIG_ERROR = 0x10118;
    /*http需要忽略的异常*/
    public static final int HTTP_IGNORE_ERROR = 0x10119;

    /**
     * 服务器定义的错误code
     */
    private int code;

    public HttpTimeException(int code, String detailMessage) {
        super(detailMessage);
        setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
