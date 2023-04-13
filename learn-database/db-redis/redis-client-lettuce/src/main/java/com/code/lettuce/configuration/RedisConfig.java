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

package com.code.lettuce.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * Redis 配置
 *
 * @author Snow
 * @date 2021/3/13 15:30
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

	/**
	 * Redis 连接池配置
	 *
	 * @return GenericObjectPoolConfig
	 */
	@Bean
	@ConfigurationProperties("spring.redis.lettuce.pool")
	public GenericObjectPoolConfig<Object> genericObjectPoolConfig() {
		return new GenericObjectPoolConfig<>();
	}

	/**
	 * Redis Server 配置
	 *
	 * @return 单机配置(还有 : RedisSentinelConfiguration 、 RedisClusterConfiguration)
	 */
	@Bean("redisServerConfig")
	@ConfigurationProperties("spring.redis")
	public RedisStandaloneConfiguration redisConfiguration() {
		return new RedisStandaloneConfiguration();
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory(GenericObjectPoolConfig<Object> poolConfig, RedisStandaloneConfiguration redisServerConfig) {
		// Redis 客户端配置
		LettucePoolingClientConfiguration redisClientConfig = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();

		// Redis 连接工厂
		return new LettuceConnectionFactory(redisServerConfig, redisClientConfig);
	}

	/**
	 * 默认情况下的模板只能支持 RedisTemplate<String, String>，也就是只能存入字符串
	 */
	@Bean
	public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		return template;
	}

}
