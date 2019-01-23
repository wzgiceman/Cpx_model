[![release](https://img.shields.io/badge/release-1.0.0-green.svg)](https://play.google.com/store/apps/details?id=com.toyscollect.prizeclaw.clawin)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# Cpx_model

![](https://github.com/wzgiceman/Cpx_model/blob/master/app/src/main/ic_launcher-web.png)

## 简介

CPX Model

包名：com.prog.zhigangwei.cpx_model

采用组件化开发，严格按照功能分包，集合Kotlin，RxJava，Retrofit，Glide，fastjson，LeakCanary等库，作为公司的基础库，适用于中小项目的快速开发

## 模块分包说明

* [app](https://github.com/wzgiceman/Cpx_model/tree/master/app) Demo
* [library](https://github.com/wzgiceman/Cpx_model/tree/master/library) 工具、依赖库模块
* [social_contact](https://github.com/wzgiceman/Cpx_model/tree/master/social_contact) 功能模块示例

## 技术要点

* [Kotlin](https://github.com/JetBrains/kotlin)
* [Rxjava](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Retrofit](https://github.com/square/retrofit)
* [Glide](https://github.com/bumptech/glide)
* [fastjson](https://github.com/alibaba/fastjson)
* [LeakCanary](https://github.com/square/leakcanary)

## Bug反馈

如果您有任何问题或反馈，请提交到[Github Issues](https://github.com/wzgiceman/Cpx_model/issues).

## 更新日志

* 2019.01.23

    1.修改BaseLazyFragment继承关系，BaseLazyFragment不再继承自BaseFragment，因为BaseLazyFragment和BaseFragment不兼容
    现在BaseLazyFragment被放置在上层，BaseFragmentManagerFragment继承自BaseLazyFragment
    
    2.修复BaseLazyFragment，现在请严格划分方法层级：initData中做数据处理，initView中做View初始化和数据绑定。
    原因是fragment销毁时，fragment中的数据不会被销毁，当fragment重新创建时，会重新调用initView将数据设置到view中
    
    3.优化BaseSaveFragment代码及注释，使代码更简洁
    
    4.修改了.gitignore，将gradle/文件夹上传，保证gradle-wrapper.properties版本一致

* 2018.12.06
    
    1.新增第三方登录
    
        Facebook登录，需要用浏览器打开 Facebook开发者平台：https://developers.facebook.com/apps/
        
        1.创建应用，将应用编号赋值给 gradle.properties 的 facebook_app_id 变量
        
        2.在Facebook开发者平台控制台的 设置->基本 中添加Android平台，填入Google Play包名、类名、密钥散列、隐私权政策、启用单点登录并保存
        
        3.在Facebook开发者平台控制台公开发布应用
        
        Google登录，需要在firebase控制台创建项目：firebase.com
        
        1.身份验证中，启用Google登录
        
        2.项目设置中，填入SHA1和包名
        
        3.下载google_service.json，放入app模块下
        
        4.在 google-service.json 中找到网页客户端ID，将其赋值给 gradle.properties 的 google_web_client_id 变量
        
        Twitter登录，需要在Twitter开发者平台：https://developer.twitter.com/en/apps
        
        1.创建应用，填写app名，描述，勾选"启用twitter登录"，填写隐私权政策,team url
        
        2.在callback url中添加：twittersdk://
        
        3.将API Key赋值给 gradle.properties 的twitter_consumer_key，API secret key赋值给 gradle.properties 的twitter_consumer_secret
    
    2.新增第三方分享
    
        1.Facebook分享：支持facebook分享文字、链接、图片、视频、多媒体
        
        facebook分享链接时，通过tag添加文字，通过quote添加引文
        
        facebook只支持分享本地视频
        
        facebook分享多媒体时，图片+视频总数最多6个
        
        分享图片、视频、多媒体时，可以通过 tag 添加文字
        
        2.Twitter分享：支持Twitter分享文字、图片
        
        分享图片时，可以通过 tag 添加文字

* 2018.11.23

    1.新增滑动返回，通过enableSwipeBack设置是否启用，默认关
    
    2.调整继承关系，修改BaseXXXActivity名字，现在的继承关系为：BaseFragmentManagerActivity继承自BaseToolsActivity继承自BaseActivity，按需使用
    
    3.新增IBaseTool，主要作用是方法命名规范，大家有合适的方法可以往里面添加，BaseToolsActivity实现了此接口
    
    4.BaseToolsActivity新增onRelease方法，按需使用
    
    5.修复了RecyclerActivity新增header之后，点击item数组越界的bug
    
    6.添加全面屏适配<meta-data android:name="android.max_aspect" android:value="2.1" />

* 2018.11.20

    1.修改了.gitignore，删除了库中已提交的.idea，gradle/等应该忽略的文件
    
    2.新增icon
    
    3.新增README
    
    4.删除了support_v7，recyclerview库，因为他们已经包含在design库中
    
    5.FirebaseUtils名字修改为DataReportUtils
    
    6.将tool基础类扩展方法Extension，ActivityExtension，FragmentExtension，ContextExtension，DialogExtension，
    扩展了showToast，jumpActivity，isValidActivity，report方法
    
    7.新增Utils类
    
    8.新增IBase接口，将Activity和Fragment模板合并
    
    9.BaseLazyFragment的hasLoadData名字修改为loading，初始化为false，在执行lazyLoadData前置为true，若手动取消或者加载错误，需要调用resetLoadingStatus方法重置加载状态，使得下次到达此页面时可以再次触发lazyLoadData刷新数据
    
    10.之前的懒加载方式为：数据加载完后调用lazyLoadDataCompleted将hasLoadData置为true，无法达到对加载数据过程加锁的效果，可能导致重复执行lazyLoadData

## LICENSE

    Copyright (c) 2018-present, CPX Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
