package com.base.router;

/**
 * Describe:路由器-注册数据
 * <p>
 * <p>
 * Created by zhigang wei
 * on 2018/4/18
 * <p>
 * <p>
 * Company :cpx
 */

public class RouterList {

    /**
     * 登录界面
     */
    public static final String LOGINT_ACTIVITY = "com.base.muslim.user.login.LoginActivity";
    /**
     * 念珠界面
     */
    public static final String TASBIH_ACTIVITY = "com.muslim.pro.imuslim.azan.social.tasbih.TasbihActivity";

    /**
     * 祈福界面
     */
    public static final String WISH_LIST_FRAGMENT = "com.muslim.pro.imuslim.azan.greeting_cards.azkar.AzkarFragment";

    /**
     * 祈福详情界面
     * 传参：
     * 1、祈福id     （intent 中 key 填写 AzkarDetailActivity.AZKAR_ID_KEY）
     * 2、祈福类型  （intent 中 key 填写 AzkarDetailActivity.AZKAR_TYPE ；
     *                  value：AzkarDetailActivity.TYPE_OFFICIAL（官方祈福）、
     *                         AzkarDetailActivity.TYPE_USER（用户发布的祈福））
     */
    public static final String AZKAR_DETAIL = "com.muslim.pro.imuslim.azan.greeting_cards.azkar.detail.AzkarDetailActivity";

    /**
     * 贺卡入口界面
     */
    public static final String GREETING_CARDS_ACTIVITY = "com.muslim.pro.imuslim.azan.greeting_cards.greetingcards.SelectImageActivity";

    /**
     * 通知界面
     */
    public static final String NOTICE_ACTIVITY = "com.muslim.pro.imuslim.azan.greeting_cards.notifications.NotificationsActivity";

    /**
     * 我的祈福界面
     */
    public static final String MY_AZKAR_ACTIVITY = "com.muslim.pro.imuslim.azan.greeting_cards.azkar.myAzkar.MyAzkarActivity";

    /**
     * 主界面
     */
    public static final String MAIN_ACTIVITY = "com.prog.muslim.ui.MainActivity";

    /**
     * 视频channel页面
     */
    public static final String CHANNEL_FRAGMENT = "com.muslim.pro.imuslim.azan.social.channel.ChannelFragment";

    /**
     * 视频详情页面
     * 传递两个参数
     * @param url {String} 视频url 必填
     * @param title {String} 标题 可选，如果添加则显示标题栏，如果标题栏为空，则标题栏隐藏
     * @param videoId {Int} 视频id 必填
     */
    public static final String CHANNEL_VIDEO_DETAIL_ACTIVITY = "com.muslim.pro.imuslim.azan.social.channel.transfer.ChannelVideoTransferActivity";


    /**
     * 真主安拉的99个名字的页面
     */
    public static final String ALLASH_99_NAMES_ACTIVITY = "com.muslim.pro.imuslim.azan.social.allahnames.AllahNamesActivity";
}
