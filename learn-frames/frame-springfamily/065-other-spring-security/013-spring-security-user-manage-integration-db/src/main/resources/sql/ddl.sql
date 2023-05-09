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

DROP SCHEMA IF EXISTS `frame_spring_security`;
CREATE SCHEMA `frame_spring_security`;
USE `frame_spring_security`;

DROP TABLE IF EXISTS `frame_spring_security`.`sys_user`;
CREATE TABLE `frame_spring_security`.`sys_user`
(
    `id`          INT(5)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `record_id`   BIGINT(32) NOT NULL UNIQUE COMMENT '唯一键',
    `account`     VARCHAR(10) DEFAULT NULL COMMENT '账号',
    `password`    VARCHAR(10) DEFAULT NULL COMMENT '密码',
    `username`    VARCHAR(10) DEFAULT NULL COMMENT '用户名',
    `age`         INT(5)      DEFAULT NULL COMMENT '年龄',
    `create_time` DATETIME    DEFAULT NOW() COMMENT '创建时间',
    `update_time` DATETIME    DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
