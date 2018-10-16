package com.base.library.retrofit_rx.Api;

/**
 * Describe:系统参数
 * <p>
 * Created by zhigang wei
 * on 2017/6/3
 * <p>
 * Company :Sichuan Ziyan
 */
public class Config {
    /**
     * 1代表Android, 2 代表 iOS
     */
    private String platform_type;
    /**
     * 使用设备号阿里云推送
     */
    private String client_id;
    /**
     * 服务端生成，验证请求合法性，登录有效期
     */
    private String token;
    /**
     * 移动端APP版本号
     */
    private String app_version;
    /**
     * 区域id
     */
    private String region_Id;
    /**
     * 用户id
     */
    private String uId;
    /**
     * 用户类型
     */
    private String userType;


    public Config() {
        setPlatform_type("1");
        setUserType("2");
    }

    public String getPlatform_type() {
        return platform_type;
    }

    public void setPlatform_type(String platform_type) {
        this.platform_type = platform_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }


    public String getRegion_Id() {
        return region_Id;
    }

    public void setRegion_Id(String region_Id) {
        this.region_Id = region_Id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
