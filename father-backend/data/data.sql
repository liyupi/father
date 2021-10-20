# 创建数据库
create database father;

# 切换数据库
use father;

# 表情
create table emoji
(
    id           int auto_increment
        primary key,
    url          varchar(4096)                      null,
    name         varchar(512)                       null,
    userId       int      default 0                 not null comment '上传用户 id',
    tags         varchar(1024)                      null comment '标签数组 json',
    reviewStatus int      default 0                 not null comment '0 - 待审核, 1 - 通过, 2 - 拒绝',
    createTime   datetime default CURRENT_TIMESTAMP null,
    isDelete     tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '表情';

create index idx_name
    on emoji (name);

# 标签
create table tag
(
    id           int auto_increment
        primary key,
    name         varchar(512)                       not null comment '标签名',
    createTime   datetime default CURRENT_TIMESTAMP null,
    isDelete     tinyint  default 0                 not null comment '是否删除 0 - 未删除 1 - 已删除',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '标签';

create index idx_name
    on tag (name);