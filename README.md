# Java Swing Modernization Engine

`注：该项目仍在初期编写中。`

**这是一个用于快速编写基于JavaSwing图形库的桌面应用程序/游戏的小型引擎，包含常用的开发工具类，并在致力于快速简洁构建GUI的同时保留直接编写Swing/AWT的方法——因为它是基于Swing的拓展而不是重写。**

## 特色

### 便捷

**它将Swing的一些功能进行了一次封装，例如全屏窗口，无边框，JAVA2D操作等功能，使您在创建、管理Swing程序的时候更加得心应手。**

### 快速

**它可以使用类似于Spring的启动方式——在应用类上注解@JSMEApplication来启动。当然，它也实现了无需单独的配置文件即可扫描项目，避免了写XML的困扰。**

**它并没有采用重新造轮子的方法来编写这个小型引擎，而是封装了一些其他优秀的开源项目——Tweeen Engine、vlcj、FastJSON、Commons-IO。使得在保证跨平台的同时使用本地功能而又不失轻便。**

## 对于其他开源项目的使用

**JSON：FastJSON**

**文件IO：Commons-IO**

**动画：Tween-Engine**

**多媒体播放：vlcj**

**Log系统：slf4j、logback**

## 开源协议

**本项目使用MIT协议作为项目开源协议。**

