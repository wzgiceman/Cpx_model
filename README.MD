[![release](https://img.shields.io/badge/release-1.2.2-green.svg)](https://play.google.com/store/apps/details?id=com.toyscollect.prizeclaw.clawin)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# Cpx_model

[![](https://is5-ssl.mzstatic.com/image/thumb/Purple128/v4/bb/a1/c8/bba1c89a-9572-ba8e-ab3d-7da8521d69c4/AppIcon-1x_U007emarketing-85-220-0-4.png/1200x630wa.png)](https://clawinapp.com/#apps)

## 简介

Clawin Android端，包名：com.toyscollect.prizeclaw.clawin

Clawin 官网：[https://clawinapp.com/#apps](https://clawinapp.com/#apps)

Google Play链接：[https://play.google.com/store/apps/details?id=com.toyscollect.prizeclaw.clawin](https://play.google.com/store/apps/details?id=com.toyscollect.prizeclaw.clawin)

Twitter主页：[https://twitter.com/ClawPrize](https://twitter.com/ClawPrize)

Facebook主页：[https://www.facebook.com/Crane-Game-Carnival-Real-Claw-Machine-Games-354962941681745/](https://www.facebook.com/Crane-Game-Carnival-Real-Claw-Machine-Games-354962941681745/)

Instagram主页：[https://www.instagram.com/clawin_carnival/](https://www.instagram.com/clawin_carnival/)

下载链接:

![](https://qr.api.cli.im/qr?data=https%253A%252F%252Fplay.google.com%252Fstore%252Fapps%252Fdetails%253Fid%253Dcom.toyscollect.prizeclaw.clawin&level=H&transparent=false&bgcolor=%23ffffff&forecolor=%23000000&blockpixel=12&marginblock=1&logourl=http%3A%2F%2Foss-cn-hangzhou.aliyuncs.com%2Fpublic-cli%2Ffree%2Ff6032e7629b603b19b2f97f5f1c58aaa1542252258.png&size=260&kid=cliim&key=b174f37d026e8e432dfdfde0cc07ad16)

## 模块分包说明（自顶向下介绍整体分包结构，介绍重要的功能分包细节，带Github跳转链接）

* [app](https://github.com/MobileCPX/Clawin/tree/master/app) 功能模块
  * [grabDolls](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/grabDolls) 首页Grab Dolls标签页
    * [room](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/grabDolls/room) 房间列表
      * [item](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/grabDolls/room/item) 房间内游戏页面
  * [activities](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/activities) 首页Activities标签页
  * [rankings](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/rankings) 首页Rankings标签页
  * [me](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/me) 首页Me标签页
    * [myDolls](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/me/myDolls) 我的娃娃
      * [applyDelivery](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/me/myDolls/applyDelivery) 邮寄页面
  * [common](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/common) 共用内容
    * [pay](https://github.com/MobileCPX/Clawin/tree/master/app/src/main/java/com/toyscollect/prizeclaw/clawin/app/common/pay) 支付
* [base](https://github.com/MobileCPX/Clawin/tree/master/base) 工具、依赖库模块
  * [clawin](https://github.com/MobileCPX/Clawin/tree/master/base/src/main/java/com/base/clawin) 共用内容
    * [login](https://github.com/MobileCPX/Clawin/tree/master/base/src/main/java/com/base/clawin/login) 登录
  * [library](https://github.com/MobileCPX/Clawin/tree/master/base/src/main/java/com/base/library)
    * [retrofit_rx](https://github.com/MobileCPX/Clawin/tree/master/base/src/main/java/com/base/library/retrofit_rx) 网络请求
    * [websocket](https://github.com/MobileCPX/Clawin/tree/master/base/src/main/java/com/base/library/websocket) WebSocket请求

## 技术要点（带Github跳转链接）

* [Kotlin](https://github.com/JetBrains/kotlin)
* [Rxjava](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Retrofit](https://github.com/square/retrofit)
* [Glide](https://github.com/bumptech/glide)
* [Lottie](https://github.com/airbnb/lottie-android)
* [fastjson](https://github.com/alibaba/fastjson)
* [LeakCanary](https://github.com/square/leakcanary)

## 更新日志（最新的写在最上面，带版本号以及每个版本更新内容简介）
* v1.2.1 
  * 修复线上bug
* v1.2.0 
  * 完成模块按功能分包
  * 替换Http请求框架
  * 优化WebSocket请求，
  * 新增推特登录
  * 新增信用卡支付
* v1.0.0 
  * 完成基础功能：登录，抓娃娃，邮寄，支付

## Bug反馈

如果您有任何问题或反馈，请提交到[Github Issues](https://github.com/MobileCPX/Clawin/issues).

## 联系作者

姓名：xxx

电话：xxx

邮箱：xxx

微信：xxx

## 联系我们

公司名称：xxx

公司网站：xxx(带链接)

Google邮箱：xxx

## LICENSE

    Copyright (c) 2018-present, (Company name) Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
