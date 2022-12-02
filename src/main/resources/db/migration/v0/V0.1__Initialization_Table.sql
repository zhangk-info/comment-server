/**
* 创建用户表
*/
drop table if exists t_user;
CREATE TABLE `t_user` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`create_by`  bigint(20) NOT NULL COMMENT '创建人id' ,
`update_by`  bigint(20) NOT NULL COMMENT '更新人id' ,
`create_user`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人名字' ,
`update_user`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人名字' ,
`gmt_create`  datetime NOT NULL COMMENT '创建时间' ,
`gmt_update`  datetime NULL COMMENT '更新时间' ,
`deleted`  int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 正常 1 删除' ,
`name`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户真实姓名' ,
`username`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户登录名' ,
`password`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户登录密码' ,
`status`  int(1)  NOT NULL DEFAULT 1 COMMENT '用户启用状态 0 禁用 1 正常 2 锁定',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;



/**
* 初始一个登录人
*/
insert into t_user(id,create_by,update_by,create_user,update_user,gmt_create,gmt_update,deleted,name,username,password,status)
    values(1,0,0,'初始化','',now(),null,0,'超级管理员','test','b0db32e2eb00b9644dcf63def75c1ac4',1);


/**
* 创建评论表
*/
drop table if exists t_comment;
CREATE TABLE `t_comment` (
`id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`create_by`  bigint(20) NOT NULL COMMENT '创建人id' ,
`update_by`  bigint(20) NOT NULL COMMENT '更新人id' ,
`create_user`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人名字' ,
`update_user`  varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人名字' ,
`gmt_create`  datetime NOT NULL COMMENT '创建时间' ,
`gmt_update`  datetime NULL COMMENT '更新时间' ,
`deleted`  int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0 正常 1 删除' ,
`article_id`  bigint(20) NOT NULL COMMENT '文章id' ,
`pid`  bigint(20) NOT NULL COMMENT '上级评论id,没有就是文章id' ,
`content`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '评论内容' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

/**
* 为评论创建索引
*/
create index IDX_COMMENT_ARTICLE_ID on t_comment (article_id);
create index IDX_COMMENT_PID on t_comment (pid);

