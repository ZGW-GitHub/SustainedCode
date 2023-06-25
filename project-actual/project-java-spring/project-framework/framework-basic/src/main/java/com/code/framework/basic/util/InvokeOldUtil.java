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

import com.code.framework.basic.domain.page.PageResp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Snow
 * @date 2023/6/24 21:00
 */
@Slf4j
@Deprecated
public class InvokeOldUtil {

	// ---------------------------------------------------- 有出入参 ----------------------------------------------------

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param            原始入参
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用目标方法的入参类型
	 * @return {@link R}
	 */
	public static <P, R> R invoke(Object param, Function<P, R> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param            原始入参
	 * @param resultClass      最终的出参类型
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用的目标方法的入参类型
	 * @return {@link R}
	 */
	public static <P, H, R> R invoke(Object param, Class<R> resultClass, Function<P, H> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		H invokeResult = invoke.apply(invokeParam);

		return BeanUtil.map(invokeResult, resultClass);
	}

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param            原始入参
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用目标方法的入参类型
	 * @return {@link R}
	 */
	public static <P, R> List<R> invokeList(Object param, Function<P, List<R>> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param            原始入参
	 * @param resultClass      最终的出参类型
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用的目标方法的入参类型
	 * @return {@link List}<{@link R}>
	 */
	public static <P, H, R> List<R> invokeList(Object param, Class<R> resultClass, Function<P, List<H>> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		List<H> invokeResult = invoke.apply(invokeParam);

		return BeanUtil.mapList(invokeResult, resultClass);
	}

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param            原始入参
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用的目标方法的入参类型
	 * @return {@link PageResp}<{@link R}>
	 */
	public static <P, R> PageResp<R> invokePage(Object param, Function<P, PageResp<R>> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param            原始入参
	 * @param resultClass      最终的出参类型
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用的目标方法的入参类型
	 * @return {@link PageResp}<{@link R}>
	 */
	public static <P, H, R> PageResp<R> invokePage(Object param, Class<R> resultClass, Function<P, PageResp<H>> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);
		PageResp<H> invokeResult = invoke.apply(invokeParam);

		return PageResp.of(invokeResult.getTotal(), BeanUtil.mapList(invokeResult.getRecords(), resultClass));
	}

	// ------------------------------------------------- 有入参，无出参 --------------------------------------------------

	/**
	 * 方法调用（入参转化，无出参）
	 *
	 * @param param            原始入参
	 * @param invoke           目标方法调用函数
	 * @param invokeParamClass 调用目标方法的入参类型
	 */
	public static <P> void invoke(Object param, Consumer<P> invoke, Class<P> invokeParamClass) {
		P invokeParam = BeanUtil.map(param, invokeParamClass);

		invoke.accept(invokeParam);
	}

	// ------------------------------------------------- 有出参，无入参 --------------------------------------------------

	/**
	 * 调用
	 * 方法调用（无入参，出参转化）
	 *
	 * @param resultClass 最终的出参类型
	 * @param invoke      目标方法调用函数
	 * @return {@link R}
	 */
	public static <R> R invoke(Class<R> resultClass, Supplier<R> invoke) {
		R invokeResult = invoke.get();

		return BeanUtil.map(invokeResult, resultClass);
	}

	/**
	 * 调用列表
	 * 方法调用（无入参，出参转化）
	 *
	 * @param resultClass 最终的出参类型
	 * @param invoke      目标方法调用函数
	 * @return {@link List}<{@link R}>
	 */
	public static <R> List<R> invokeList(Class<R> resultClass, Supplier<List<R>> invoke) {
		List<R> invokeResult = invoke.get();

		return BeanUtil.mapList(invokeResult, resultClass);
	}

}
