# 数据库初始化

-- 创建库
create database if not exists usercenter;

-- 切换库
use usercenter;

# 用户表
create table user
(
    username     varchar(100)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(50)                        null comment '账号',
    avatarUrl    varchar(512)                       null comment '用户头像',
    gender       tinyint  default 0                 null comment '性别 0 - 未知',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(20)                        null comment '电话',
    email        varchar(100)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    userNumber   varchar(512)                       null comment '编号',
    constraint email
        unique (email),
    constraint userAccount
        unique (userAccount)
)
    comment '用户';

# 导入示例用户
INSERT INTO usercenter.user (username, userAccount, avatarUrl, gender, userPassword, phone, email, userStatus, createTime, updateTime, isDelete, userRole, userNumber) VALUES ('zhouzhou', 'zhouzhou', 'https://himg.bdimg.com/sys/portraitn/item/public.1.e137c1ac.yS1WqOXfSWEasOYJ2-0pvQ', null, 'b0dd3697a192885d7c055db46155b26a', null, null, 0, '2023-08-06 14:14:22', '2023-08-06 14:39:37', 0, 1, '1');

