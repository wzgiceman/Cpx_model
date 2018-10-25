package com.base.library.retrofit_rx.exception;

/**
 * 回调统一请求异常
 * Created by WZG on 2016/12/12.
 */

public class ApiException extends Exception {
    /*错误码*/
    private int code;
    /*给用户显示的信息*/
    private String displayMessage;
    /*给开发显示的原始错误信息*/
    private String message;



    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(Throwable cause, int code, String showMsg) {
        super(showMsg, cause);
        setCode(code);
        setDisplayMessage(showMsg);
        if(cause==null)return;
        setMessage(cause.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
