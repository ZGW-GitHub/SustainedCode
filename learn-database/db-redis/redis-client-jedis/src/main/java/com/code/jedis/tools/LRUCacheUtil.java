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

package com.code.jedis.tools;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Snow
 * @date 2021/8/31 15:37
 */
@Slf4j
public class LRUCacheUtil<K, V> extends LinkedHashMap<K, V> {

	private final int CACHE_SIZE;

	/**
	 * @param cacheSize 能缓存多少数据
	 */
	public LRUCacheUtil(int cacheSize) {
		// 第三个参数是设置 map 的排序模式，true 表示根据访问顺序排序，最近访问的放在前面
		super((int) (Math.ceil(cacheSize / 0.75) + 1), 0.75f, true);
		this.CACHE_SIZE = cacheSize;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > CACHE_SIZE; // 意思就是当 map 中的数据量大于 CACHE_SIZE 时，自动删除最老的数据
	}

}
