# 云计算安全实践

## 日程安排

| 日期  | 预期                                                         |
| ----- | ------------------------------------------------------------ |
| 06/12 | 1、到图书馆借书《云存储安全实践》，把第一章概览看了，熟悉我们要实现的所有功能；<br />2、能在本地跑一跑第三章的系统基本功能/基础安全服务代码就先跑一跑，先预习一下。今天没跑通的话明天也得跑通；<br />3、开个github仓库，每天下午4点半左右我们开个腾讯会议跟一下当天进度，并上传自己这部分的代码；<br />4、开个协同工作文档，把每天调通的功能点写在文档里。 |
| 06/13 | 1、调通第5章更新、测试与发布部分<br />2、调通第3章基础安全服务部分 |
| 06/14 | 1、调通第4章数据安全服务部分<br />2、编写完毕“三权分立”部分代码 |
| 06/15 | 1、编写“数据删除”部分代码<br />2、整理代码和知识点           |
| 06/16 | 汇报                                                         |

## 功能点实现列表

tips：橙色部分为拓展部分，全部勾完本课程即为满分

- [ ] 基础安全服务
  - [x] 3.2.2 用户标识服务
    - [x] 3.2.2.1 注册界面实现（client）
    - [x] 3.2.2.2 标识信息生成（client）
    - [x] 3.2.2.3 标识信息传输（json）
    - [x] 3.2.2.4 标识信息存储（server）
    - [ ] 3.2.2.5 ~~标识信息管理（server）~~
  - [x] 3.2.3 用户鉴别服务
    - [x] 3.2.3.1 用户鉴别界面实现（client）
    - [x] 3.2.3.2 用户鉴别信息计算（client）
    - [x] 3.2.3.3 用户鉴别请求发送（json）
    - [x] 3.2.3.4 用户鉴别请求验证（server）
    - [x] 3.2.3.5 用户鉴别信息管理（server）
  - [x] 3.2.4 访问控制服务
    - [x] 针对用户身份的拦截器实现（server）
  - [x] 3.2.5 安全审计服务
    - [x] 3.2.5.1 审计日志的生成和存储（server）
    - [x] 3.2.5.2 审计日志的获取和查询（server）
  - [ ] 3.2.6三权分立（得自己写）
    - [ ] 3.2.6.1 安全审计员客户端及服务端（client&&json&&server）
    - [ ] 3.2.6.2 安全保密管理员客户端及服务端（client&&json&&server）
    - [ ] 3.2.6.3 系统管理员客户端及服务端（client&&json&&server）
- [ ] 数据安全服务
  - [x] 4.2.1 普通用户客户端（client）
  - [x] 客户端本地数据信息生成
    - [x] 4.2.2 数据加解密（client）
    - [x] 4.2.3 数据元信息生成（client）
    - [x] 4.2.4 密文索引生成（client）
  - [x] 4.2.5 数据上传
    - [x] 4.2.5.1 文件选择框（client）
    - [x] 4.2.5.2 数据上传界面设计（client）
    - [x] 4.2.5.3 数据上传过程设计（client）
  - [x] 4.2.6 数据存储
    - [x] 4.2.6.1 服务端接收数据（server）
    - [x] 4.2.6.2 数据元信息存储（server）
    - [x] 4.2.6.3/4.2.6.4 服务端与云存储平台对接（server&&cloud）
  - [ ] 4.2.7 数据列出（列目录树）
    - [ ] 4.2.7.1 服务端数据列出（server）
    - [ ] 4.2.7.2 客户端数据列出（client）
  - [x] 4.2.8 数据下载和打开
    - [x] 4.2.8.1 服务端数据下载（server&&cloud）
    - [x] 4.2.8.2客户端数据下载（client&&server）
    - [x] 4.2.8.3客户端数据打开（client）
  - [ ] 4.2.9 数据分享（cloud？）
    - [x] 4.2.9.1 客户端与服务端间分享数据（client&&server）
    - [x] 4.2.9.2 获取分享数据列表（client&&server）
    - [ ] 4.2.9.3 取消分享（client&&server）(前端没有刷新)
    - [x] 4.2.9.4 获取被分享数据列表（client&&server）
    - [ ] 4.2.9.5 被分享数据的下载和打开（client&&server&&cloud）
  - [x] 4.2.10 数据检索
    - [x] 4.2.6.5 服务端与云安全服务平台对接
    - [x] 4.2.10.1 服务端数据检索（server&&云安全服务平台&&cloud）
      - [x] 元信息检索
      - [x] **密文检索**
    - [x] 4.2.10.2 客户端数据检索（client）
  - [ ] 4.2.11 数据删除（得自己写DataTransmissionController接口）
    - [ ] 客户端有删除按钮，服务端已添加删除接口，需写一个槽函数调用接口
- [ ] 系统的更新、测试和发布
  - [x] 客户端在线更新
    - [x] 5.1.1 服务端版本更新接口（server）
    - [x] 5.1.2 客户端在线更新（client）
  - [x] 测试
  - [ ] 打包
    - [ ] 5.3.1 客户端打包（client）
      - [ ] 5.3.1.1 添加管理员权限
      - [ ] 5.3.1.2 生成发布程序
      - [ ] 5.3.1.3 打包依赖库
      - [ ] 5.3.1.4 生成客户端安装包
    - [ ] 5.3.2 服务端打包发布（server）
      - [ ] 5.3.2.1 客户端安装包网上发布
      - [ ] 5.3.2.2 服务端项目打包
      - [ ] 5.3.2.3 服务端系统发布