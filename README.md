## 多级评论测试项目

### 主要组件：

* springboot
* oauth2 & spring security
* flyway
* mysql/db2

### 主要类介绍

* CurrentUserFilter token解析并放入CurrentUser
* PermissionCheckService 自定义权限校验
* TreeUtil list转树形工具类
* 
#### 测试类：CommentControllerTest
#### 文档地址： http://localhost:92/doc.html#/home

### 实现思路 CommentServiceImpl.tree()

1. 新增数据时记录上级评论id和第一层评论id
2. 查询时先分页查询第一层评论，再根据第一层评论ids查询出所有子集评论（其实也可以用中间表记录所有pids的方式，但是没有强制要求要查出第二层的所有子集就没选用这个方案）
3. 将所有数据放一起后根据id和pid进行树化