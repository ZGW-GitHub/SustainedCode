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

CREATE DATABASE IF NOT EXISTS `frame_spring_security`;

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user
(
    `id`       INT(11)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `account`  varchar(32) not null default '' comment '用户账号',
    `password` varchar(64) not null default '' comment '用户密码',
    `salt`     varchar(32) not null default '' comment '盐',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

COMMIT;
