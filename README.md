# 粤盟管理系统（包括客户端和服务端）
基于目前全国正在发展的100多所高校粤语社，粤盟作为一个服务于各大高校社团的组织，特定开发一个管理平台让工作部成员去辅助社团的发展。<br>
此系统在网页版已上线,[粤盟管理系统网页版](http://www.jyut.cc/wx_CCAUCC_pc.html), 安卓客户管理平台正在开发中<br>

## **一.开发环境** ##
windows8 + Java8 + Android Sdk API23 + Eclipse Android(Neon) + Android Studio1.5 + Eclipse Jee(Neon) + Tomcat8.0 + MySQL Server5.6<br>

## **二.开源框架** ##
- com.yolanda.nohttp　－　基于OKHttp的网络请求框架,可自定义请求类
- com.astuetz.PagerSlidingTabStrip　－　ViewPager的滑动带,轻量级的ViewPager辅助用品
- com.github.Syehunter.RecyclerViewManager　－　Recyclerview的管理工具,可添加上拉刷新和下拉加载的部分,不过似乎没有setSelection这方法,比较坑
- com.jakewharton.butterknife　－　一个方便使用的view注入工具类,省下很多findViewById的事情
- com.alibaba.fastjson　－　强大的json处理工具,速度也是一流<br>

## **三.主要技术** ##
- 使用Tomcat保持与客户端保持通信
- Servlet访问Mysql数据库并与客户端产生数据交互——增、改、查
- 采用 Material Design 设计理念,使用多种Appcompat包与Design包中的Widget设计UI
- 简单的MD5与AES加密 
- 分页加载技术
- 缓存技术

##**四.功能模块**##
请看以下GIF动画<br>
<p>↓↓登录界面 -> 主界面 -> 社团表（主要功能）-- 消息列表 -- 工作部管理功能 -- 管理者功能（需具有较高权限）↓↓
</p>
![](https://github.com/wztscau/jyut-system/raw/master/gif/001.gif)  
<p>↓↓工作部成员管理界面,需较高权限成员才能进行更改和增加成员,普通成员可以查询(主要功能)↓↓
</p>
![](https://github.com/wztscau/jyut-system/raw/master/gif/002.gif)  
<p>↓↓社团表功能,所有工作部成员皆可对所有社团进行增、改、查,普通社团成员亦可获得查询功能,搜索时默认对查询所有社团,搜索字段包括(学校名/社团名/负责人姓名), 亦可以对某个地区进行搜索处理,由于手机屏幕较小,因此在首页板块只能显示地区、姓名与学校名,点击条目后就能够浏览个人所对应的社团信息。↓↓
</p>
![](https://github.com/wztscau/jyut-system/raw/master/gif/003.gif)  
<p>↓↓8个基于工作部专员使用的模块暂时只开发了2个,即账号分配与新增学校.账号分配是给新注册的成员分配工作职责,新增学校用于防止在输入社团信息时误输入错误的学校名称或不存在的学校↓↓
</p>
![](https://github.com/wztscau/jyut-system/raw/master/gif/004.gif)  

