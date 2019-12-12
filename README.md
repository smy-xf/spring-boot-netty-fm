#spring-boot-netty-fm
 spring-boot-netty-fm 
 
带你从无到有的开发一个仿微信的聊天App，其中涉及 Netty的websocket开发、SpringBoot开发、MUI与H5Plus（H5+）相关知识点开发 和Nginx+FastDFS分布式文件系统搭建与使用等，整个课程一气呵成。学习之后自己也能开发出一个高颜值高水平的App，并且发布上线。

相关知识点： 
1、Netty介绍与相关基础知识 初识Netty BIO讲解 NIO讲解 BIO NIO AIO的区别与理解 Netty的三种线程模型

2、使用Netty构建 websocket服务器 编写websocket与 子处理器initialzer 编写chatHandler对消息的处理 基于js的websocket相关api介绍 简单实现前后端聊天通信

3、构建项目 MUI,H5+，Hbuilder介绍 创建项目，构建基本页面布局结构 数据库表设计 SpringBoot整合MyBatis 使用SpringBoot整合Netty搭建后台

4、用户注册/登录/个人信息 选择照片与图片剪裁插件使用 FastDFS+Nginx分布式高性能文件服务 保存照片到相册 用户唯一二维码生成与展示和下载

5、发现页面与通讯录 搜索好友功能开发 扫一扫二维码添加好友功能开发 通讯录模板使用与开发 构建好友数据结构以及通用方法渲染 到通讯录

6、聊天业务开发 构建前端聊天业务的模型 Netty处理handler编写 Netty点对点代聊天业务开发 聊天记录保存与已读未读状态标记 聊天记录左滑删除

7、心跳机制 Netty心跳处理及读写超时设置 关闭wifi或3g/4g 及关闭进程后的测试 标记离线消息未签收并保存到服务器 重新上线后获取未签收消息并标记签收 无读写请求时保持心跳，设置keepalive

8、云部署 云服务器购买以及安全组配置讲解 环境搭建与配置 SpringBoot项目打包与上传 App应用云打包与相关注意事项 最终生产环境测试与多手机联调