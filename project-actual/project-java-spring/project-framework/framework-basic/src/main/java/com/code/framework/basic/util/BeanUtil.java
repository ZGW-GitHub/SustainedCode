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

package com.code.framework.basic.util;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author Snow
 * @date 2023/6/24 20:19
 */
@Slf4j
public class BeanUtil {

	public static <S, T> T map(S source, Class<T> targetClass) {
		if (null == source) {
			return null;
		} else {
			T target = BeanUtils.instantiateClass(targetClass);
			copyProperties(source, target);
			return target;
		}
	}

	public static <S, T> List<T> mapList(Collection<S> sourceList, Class<T> targetClass) {
		if (sourceList == null) {
			return null;
		}

		List<T> targetList = Lists.newArrayList();
		for (S source : sourceList) {
			T target = map(source, targetClass);
			targetList.add(target);
		}
		return targetList;
	}

	public static <S, T> void copyProperties(S source, T target, String... ignoreProperties) {
		if (null != source && null != target) {
			BeanUtils.copyProperties(source, target, ignoreProperties);
		}
	}

}
