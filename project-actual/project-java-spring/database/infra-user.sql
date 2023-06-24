/*
 * Copyright (C) <2023> <Snow>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

BEGIN;
# 开启事务

# 创建数据库：infra_user
create database if not exists `infra_user` charset utf8;
use `infra_user`;

# 创建用户表
drop table if exists `user_info`;
create table `user_info`
(
    `id`          bigint(20) unsigned not null auto_increment comment '主键' primary key,
    `record_no`   bigint(20) unsigned not null default 0 comment '唯一键',
    `account`     varchar(32)         not null default '' comment '用户账号',
    `password` varchar(64) not null default '' comment '用户密码',
    `salt`     varchar(32) not null default '' comment '盐',
    `nickname`    varchar(10)         not null default '' comment '昵称',
    `avatar`      varchar(32)         not null default '' comment '头像',

    `is_del`      tinyint(1) unsigned not null default 0 comment '逻辑删除',
    `creator`     bigint(20) unsigned not null default 0 comment '创建人',
    `create_time` datetime            not null default CURRENT_TIMESTAMP comment '创建时间',
    `updater`     bigint(20) unsigned not null default 0 comment '更新人',
    `update_time` datetime            not null default '1000-01-01 00:00:00' on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_record_no unique (record_no),
    key inx_account (account)
);

COMMIT; # 提交事务