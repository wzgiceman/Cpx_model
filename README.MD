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
