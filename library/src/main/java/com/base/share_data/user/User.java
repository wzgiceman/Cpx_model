package com.base.share_data.user;

import com.alibaba.fastjson.JSONObject;
import com.base.library.retrofit_rx.Api.BaseApi;
import com.base.library.retrofit_rx.RxRetrofitApp;
import com.base.library.utils.AbSharedUtil;

/**
 * Describe:用户
 * <p>
 * Created by zhigang wei
 * on 2018/4/19
 * <p>
 * Company :cpx
 */
public class User {
    private String id;
    private String ui_id="";
    private String userName;
    private String nikeName;
    private String mobile;
    private String email;
    private String psd;
    private String token;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 绑定方式
     */
    private String bindType;
    /**
     * 播放设置 0不处理 1循环播放 2播放完成后切换下一章节
     */
    private String spPlayType;
    /**
     * 经文播放循环次数
     */
    private int rePlaySp;
    /**
     * 经纬度
     */
    private double lat;
    private double lng;
    /**
     * 时间格式12/24
     */
    private Integer timeFormat;
    /**
     * 城市-计算方式
     */
    private Integer calcMethod=0;
    /**
     * 派系
     */
    private Integer asrJuristic;
    /**
     * 高纬度
     */
    private Integer adjustHighLats;

    /**
     * 今天时间
     */
    private String tody = "";

    /**
     * 所在城市
     */
    private String city = "";

    /**
     * 朝向偏移量
     */
    private double newAngle;
    /**
     * 首页背景
     */
    private String homeBg = "file:///android_asset/wallpaper/wallpaper_5.png";
    /**
     * 指南针图片-指针
     */
    private String kaabasSkin = "file:///android_asset/compass/compass_bg_1.png";
    private String kaabasPoint = "file:///android_asset/compass/compass_pointer_1.png";

    /**
     * app语言
     */
    private String localLg;
    /**
     * 音译
     */
    private String transliteration;
    /**
     * 翻译
     */
    private String translate;
    private String translateMsg;
    /**
     * mp3语言
     */
    private String mp3;
    private String mp3Msg;
    /**
     * 阅读进度纪录暂时开关
     */
    private boolean scriptureProgress;
    /**
     * 阅读进度位置
     */
    private String scriptureProgressP;

    /**
     * 刷新控制-1刷新壁纸 2朝拜时间 3手动定位刷新数据 4刷新指南针 5经文更新 6城市刷新,切换指兰针转向 7古兰经切换文本样式 8日夜模式切换
     *
     */
    private int refresh;

    /**
     * 上一次和服务器同步的时间
     */
    private long preUpdateTime;

    /**
     * 是否是登录模式
     */
    private boolean login;

    /***
     *当前用户版本
     */
    private String version;

    /**
     * 是否已经进入app
     */
    private boolean inAppMore;

    /**
     * firebase通知的tokean信息
     */
    private String firebaseTokean = "";

    /**
     * 古兰经字体设置
     */
    private String spFont;
    private String spFontTtf;

    /**
     * 古兰经阅读背景
     */
    private String spReadBg = "file:///android_asset/wallpaper/sp_wallpaper_1.png";


    /**
     * 登录成功以后设置用户信息
     *
     * @param resulte
     */
    public void setLoginUser(String resulte) {
        JSONObject object = JSONObject.parseObject(resulte);
        setUserName(object.getString("username"));
        setToken(object.getString("token"));
        setEmail(object.getString("email"));
        setUi_id("");
        if (object.containsKey("avatar")) {
            setAvatar(object.getString("avatar"));
        }else{
            setAvatar("");
        }
        setLogin(true);
    }

    public String getSpReadBg() {
        return spReadBg;
    }

    public void setSpReadBg(String spReadBg) {
        this.spReadBg = spReadBg;
    }

    public String getFirebaseTokean() {
        return firebaseTokean;
    }

    public void setFirebaseTokean(String firebaseTokean) {
        this.firebaseTokean = firebaseTokean;
    }

    public boolean isInAppMore() {
        return inAppMore;
    }

    public void setInAppMore(boolean inAppMore) {
        this.inAppMore = inAppMore;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public long getPreUpdateTime() {
        return preUpdateTime;
    }

    public void setPreUpdateTime(long preUpdateTime) {
        this.preUpdateTime = preUpdateTime;
    }

    public String getLgAll() {
        return mp3 + "|" + transliteration + "|" + translate;
    }

    public boolean isScriptureProgress() {
        return scriptureProgress;
    }


    public int getRePlaySp() {
        return rePlaySp;
    }

    public void setRePlaySp(int rePlaySp) {
        this.rePlaySp = rePlaySp;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }


    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Integer getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(Integer timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Integer getCalcMethod() {
        return this.calcMethod;
    }

    public void setCalcMethod(Integer calcMethod) {
        this.calcMethod = calcMethod;
    }

    public Integer getAsrJuristic() {
        return this.asrJuristic;
    }

    public void setAsrJuristic(Integer asrJuristic) {
        this.asrJuristic = asrJuristic;
    }

    public Integer getAdjustHighLats() {
        return this.adjustHighLats;
    }

    public void setAdjustHighLats(Integer adjustHighLats) {
        this.adjustHighLats = adjustHighLats;
    }


    public String getTody() {
        return this.tody;
    }

    public void setTody(String tody) {
        this.tody = tody;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getHomeBg() {
        return this.homeBg;
    }


    public void setHomeBg(String homeBg) {
        this.homeBg = homeBg;
    }


    public double getNewAngle() {
        return this.newAngle;
    }


    public void setNewAngle(double newAngle) {
        this.newAngle = newAngle;
    }


    public String getKaabasSkin() {
        return this.kaabasSkin;
    }


    public void setKaabasSkin(String kaabasSkin) {
        this.kaabasSkin = kaabasSkin;
    }


    public String getKaabasPoint() {
        return this.kaabasPoint;
    }


    public void setKaabasPoint(String kaabasPoint) {
        this.kaabasPoint = kaabasPoint;
    }


    public String getLocalLg() {
        return this.localLg;
    }


    public void setLocalLg(String localLg) {
        this.localLg = localLg;
        AbSharedUtil.putString(RxRetrofitApp.getApplication(), "token", getLocalLg() + "&" + getToken());
    }


    public String getTransliteration() {
        return this.transliteration;
    }


    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }


    public String getTranslate() {
        return this.translate;
    }


    public void setTranslate(String translate) {
        this.translate = translate;
    }


    public String getMp3() {
        return this.mp3;
    }


    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }


    public boolean getScriptureProgress() {
        return this.scriptureProgress;
    }


    public String getUi_id() {
        return ui_id;
    }

    public void setUi_id(String ui_id) {
        this.ui_id = ui_id;
    }

    public void setScriptureProgress(boolean scriptureProgress) {
        this.scriptureProgress = scriptureProgress;
    }


    public String getScriptureProgressP() {
        return this.scriptureProgressP;
    }


    public void setScriptureProgressP(String scriptureProgressP) {
        this.scriptureProgressP = scriptureProgressP;
    }


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTranslateMsg() {
        return this.translateMsg;
    }


    public void setTranslateMsg(String translateMsg) {
        this.translateMsg = translateMsg;
    }

    public String getMp3Msg() {
        return this.mp3Msg;
    }

    public void setMp3Msg(String mp3Msg) {
        this.mp3Msg = mp3Msg;
    }


    public String getUserName() {
        return this.userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNikeName() {
        return this.nikeName;
    }


    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }


    public String getMobile() {
        return this.mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPsd() {
        return this.psd;
    }


    public void setPsd(String psd) {
        this.psd = psd;
    }


    public String getBindType() {
        return this.bindType;
    }


    public void setBindType(String bindType) {
        this.bindType = bindType;
    }


    public String getSpPlayType() {
        return this.spPlayType;
    }


    public void setSpPlayType(String spPlayType) {
        this.spPlayType = spPlayType;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        String config = getLocalLg() + "&" + token;
        AbSharedUtil.putString(RxRetrofitApp.getApplication(), "token", config);
        BaseApi.setConfig(config);
    }

    public String getSpFont() {
        return spFont;
    }

    public void setSpFont(String spFont) {
        this.spFont = spFont;
    }

    public String getSpFontTtf() {
        return spFontTtf;
    }

    public void setSpFontTtf(String spFontTtf) {
        this.spFontTtf = spFontTtf;
    }


}
