package com.base.library.rxRetrofit.api.result;

/**
 * Describe:http数据返回基础类
 * <p>
 * Created by zhigang wei
 * on 2017/5/24
 * <p>
 * Company :Sichuan Ziyan
 */
public class BaseResult {
    private int code;
    private String msg;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
