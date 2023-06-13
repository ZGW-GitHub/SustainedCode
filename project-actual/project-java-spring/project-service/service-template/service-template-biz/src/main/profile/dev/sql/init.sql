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

CREATE DATABASE IF NOT EXISTS project_actual CHARSET utf8;

DROP TABLE IF EXISTS project_actual.template;
CREATE TABLE project_actual.template
(
    `id`          INT(11)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    `record_no`   VARCHAR(50) NOT NULL UNIQUE COMMENT '唯一键',
    `name`        VARCHAR(10)          DEFAULT NULL COMMENT '姓名',
    `age`         INT(5)               DEFAULT NULL COMMENT '年龄',
    `delete`      TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `creator`     VARCHAR(30) NOT NULL COMMENT '创建人',
    `create_time` DATETIME             DEFAULT NOW() COMMENT '创建时间',
    `updater`     VARCHAR(30)          DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME             DEFAULT NULL ON UPDATE NOW() COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;