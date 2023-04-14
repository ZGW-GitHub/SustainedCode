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

package com.code.sharding.jdbc.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.util.spi.type.typed.TypedSPI;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Snow
 * @date 2023/4/6 17:50
 */
@Slf4j
public class SnowRangeShardingAlgorithm implements StandardShardingAlgorithm<Long>, TypedSPI {

	/**
	 * 进行切分
	 *
	 * @param availableTargetNames 所有分片库/表的集合
	 * @param shardingValue        分片值
	 * @return {@link String} 	   目标库/表
	 */
	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
		long suffix = shardingValue.getValue() % availableTargetNames.size();

		for (String availableTargetName : availableTargetNames) {
			if (availableTargetName.endsWith(String.valueOf(suffix))) {
				log.debug("分片策略(精准) >>> value = {} , target = {}", shardingValue.getValue(), availableTargetName);
				return availableTargetName;
			}
		}
		throw new IllegalArgumentException("匹配不到库/表");
	}

	/**
	 * 进行切分
	 *
	 * @param availableTargetNames 所有分片库/表的集合
	 * @param shardingValue        分片值
	 * @return {@link Collection}<{@link String}> 目标库/表
	 */
	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
		Set<String> result = new LinkedHashSet<>();

		// between and 的起始值
		long lower = shardingValue.getValueRange().lowerEndpoint();
		long upper = shardingValue.getValueRange().upperEndpoint();

		// 循环范围计算分库逻辑
		for (long i = lower; i <= upper; i++) {
			for (String databaseName : availableTargetNames) {
				if (databaseName.endsWith(String.valueOf(i % availableTargetNames.size()))) {
					result.add(databaseName);
				}
			}
		}

		log.debug("分片策略(范围) >>> value = {}-{} , target = {}", lower, upper, result);
		return result;
	}

	/**
	 * Get type.
	 *
	 * @return type
	 */
	@Override
	public String getType() {
		return "standard";
	}

}
