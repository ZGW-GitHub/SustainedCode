/******************************************************** 库 1 ********************************************************/
-- db1
CREATE
    DATABASE IF NOT EXISTS sharding_db0 CHARSET utf8;
USE
    sharding_db0;

-- table1
DROP TABLE IF EXISTS user_0;
CREATE TABLE user_0
(
    id          INT(5)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_id   BIGINT(32) NOT NULL UNIQUE COMMENT '唯一键',
    name        VARCHAR(10) DEFAULT NULL COMMENT '姓名',
    age         INT(5)      DEFAULT NULL COMMENT '年龄',
    create_time DATETIME    DEFAULT NOW() COMMENT '创建时间',
    update_time DATETIME    DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_record_id (record_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- table2
DROP TABLE IF EXISTS user_1;
CREATE TABLE user_1
(
    id          INT(5)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_id   BIGINT(32) NOT NULL UNIQUE COMMENT '唯一键',
    name        VARCHAR(10) DEFAULT NULL COMMENT '姓名',
    age         INT(5)      DEFAULT NULL COMMENT '年龄',
    create_time DATETIME    DEFAULT NOW() COMMENT '创建时间',
    update_time DATETIME    DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_record_id (record_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

/******************************************************** 库 2 ********************************************************/

-- db2
CREATE
    DATABASE IF NOT EXISTS sharding_db1 CHARSET utf8;
USE
    sharding_db1;

-- table1
DROP TABLE IF EXISTS user_0;
CREATE TABLE user_0
(
    id          INT(5)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_id   BIGINT(32) NOT NULL UNIQUE COMMENT '唯一键',
    name        VARCHAR(10) DEFAULT NULL COMMENT '姓名',
    age         INT(5)      DEFAULT NULL COMMENT '年龄',
    create_time DATETIME    DEFAULT NOW() COMMENT '创建时间',
    update_time DATETIME    DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_record_id (record_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

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

-- table2
DROP TABLE IF EXISTS user_1;
CREATE TABLE user_1
(
    id          INT(5)     NOT NULL AUTO_INCREMENT COMMENT '主键',
    record_id   BIGINT(32) NOT NULL UNIQUE COMMENT '唯一键',
    name        VARCHAR(10) DEFAULT NULL COMMENT '姓名',
    age         INT(5)      DEFAULT NULL COMMENT '年龄',
    create_time DATETIME    DEFAULT NOW() COMMENT '创建时间',
    update_time DATETIME    DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE INDEX uk_record_id (record_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
