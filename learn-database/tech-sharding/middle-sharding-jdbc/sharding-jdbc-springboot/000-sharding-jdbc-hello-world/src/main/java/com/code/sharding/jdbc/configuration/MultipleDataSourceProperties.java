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

package com.code.sharding.jdbc.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Snow
 * @date 2023/2/15 23:14
 */
@Data
@ConfigurationProperties("jdbc")
public class MultipleDataSourceProperties {

    @Value("${jdbc.simple.url}")
    private String simpleUrl;
    @Value("${jdbc.simple.username}")
    private String simpleUsername;
    @Value("${jdbc.simple.password}")
    private String simplePassword;
    @Value("${jdbc.simple.driver}")
    private String simpleDriver;

    @Value("${jdbc.sharding.url}")
    private String shardingUrl;
    @Value("${jdbc.sharding.driver}")
    private String shardingDriver;

}
