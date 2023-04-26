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

package com.code.jedis.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

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
	 * @return JedisPoolConfig
	 */
	@Bean
	@ConfigurationProperties("spring.redis.jedis.pool")
	public JedisPoolConfig jedisPoolConfig() {
		return new JedisPoolConfig();
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
	public JedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, RedisStandaloneConfiguration redisServerConfig) {
		// Redis 客户端配置
		JedisClientConfiguration redisClientConfig = JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig).build();
		redisClientConfig.getConnectTimeout();

		// Redis 连接工厂
		return new JedisConnectionFactory(redisServerConfig, redisClientConfig);

	}

	/**
	 * Jedis 连接池
	 *
	 * @param jedisPoolConfig Redis 连接池配置
	 *
	 * @return JedisPool
	 */
	@Bean
	public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig, RedisStandaloneConfiguration redisServerConfig) {
		return new JedisPool(jedisPoolConfig, redisServerConfig.getHostName(), redisServerConfig.getPort(), Protocol.DEFAULT_TIMEOUT,
							 String.valueOf(redisServerConfig.getPassword().get()), redisServerConfig.getDatabase());
	}

	/**
	 * 默认情况下的模板只能支持 RedisTemplate<String, String>，也就是只能存入字符串
	 */
	@Bean
	public RedisTemplate<String, Serializable> redisCacheTemplate(JedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		return template;
	}

}
