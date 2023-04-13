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

package com.code.clazz.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Snow
 * @date 2022/8/29 14:15
 */
@Slf4j
public class MethodTest {

	@Test
	void test1() {

	}

	@SuppressWarnings("all")
	private static void printMethod(Class<?> clazz) {
		Method[] myselfMethodArray = clazz.getDeclaredMethods();
		List<Method> myselfMethodList = Arrays.stream(myselfMethodArray)
											  .filter(m -> Modifier.isPublic(m.getModifiers()))
											  .filter(m -> !m.isBridge())
											  .collect(Collectors.toList());

		Method[] allMethodArray = clazz.getMethods();
		List<Method> parentMethodList = Arrays.stream(allMethodArray).filter(m -> {
			boolean objectMethod       = Object.class.equals(m.getDeclaringClass());
			boolean serializableMethod = Serializable.class.equals(m.getDeclaringClass());
			boolean toStringMethod     = "toString".equals(m.getName());

			return (!objectMethod && !serializableMethod && !toStringMethod);
		}).collect(Collectors.toList());
		parentMethodList.removeAll(myselfMethodList);
		List<String> parentMethodStringList = parentMethodList.stream().map(m -> makeMethodSimpleString(m)).collect(Collectors.toList());

		List<Method> overrideMethodList = myselfMethodList
				.stream().filter(m -> parentMethodStringList.contains(makeMethodSimpleString(m))).collect(Collectors.toList());
		List<String> overrideMethodStringList = overrideMethodList.stream().map(m -> makeMethodSimpleString(m)).collect(Collectors.toList());

		myselfMethodList.removeAll(overrideMethodList);
		parentMethodList.removeAll(overrideMethodList);

		parentMethodList = parentMethodList.stream()
										   .filter(m -> !overrideMethodStringList.contains(makeMethodSimpleString(m)))
										   .sorted(Comparator.comparing(s -> ((Method) s).getName())
															 .thenComparing(s -> makeMethodSimpleString((Method) s).length()))
										   .collect(Collectors.toList());
		overrideMethodList = overrideMethodList.stream()
											   .sorted(Comparator.comparing(s -> ((Method) s).getName())
																 .thenComparing(s -> makeMethodSimpleString((Method) s).length()))
											   .collect(Collectors.toList());
		myselfMethodList = myselfMethodList.stream()
										   .filter(m -> !overrideMethodStringList.contains(makeMethodSimpleString(m)))
										   .sorted(Comparator.comparing(s -> ((Method) s).getName())
															 .thenComparing(s -> makeMethodSimpleString((Method) s).length()))
										   .collect(Collectors.toList());


		System.out.println("# 继承的方法");
		parentMethodList.stream().forEach(m -> {System.out.printf("- `%s`\n", makeMethodString(m));});
		System.out.println("# 重写的方法");
		overrideMethodList.forEach(m -> {System.out.printf("- `%s`\n", makeMethodString(m));});
		System.out.println("# 自身方法");
		myselfMethodList.stream().forEach(m -> {System.out.printf("- `%s`\n", makeMethodString(m));});
	}

	private static String makeMethodString(Method m) {
		return m.getReturnType().getSimpleName() + " " + makeMethodSimpleString(m);
	}

	private static String makeMethodSimpleString(Method m) {
		return m.getName() + makeMethodParametersString(m);
	}

	private static String makeMethodParametersString(Method m) {
		StringBuilder sb         = new StringBuilder();
		Parameter[]   parameters = m.getParameters();
		sb.append("(");
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			if (i == 0) {
				sb.append(parameter.getType().getSimpleName()).append(" ").append(parameter.getName());
			} else {
				sb.append(", ").append(parameter.getType().getSimpleName()).append(" ").append(parameter.getName());
			}
		}
		sb.append(")");

		return sb.toString();
	}

}
