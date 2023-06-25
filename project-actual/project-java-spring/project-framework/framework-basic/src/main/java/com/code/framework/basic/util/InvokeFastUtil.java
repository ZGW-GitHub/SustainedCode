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
public class InvokeFastUtil {

	// ---------------------------------------------------- 有出入参 ----------------------------------------------------

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param               原始入参
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用目标方法的入参类型
	 * @return {@link R}
	 */
	public static <P, R> R invoke(Object param, Function<P, R> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param               原始入参
	 * @param resultSupplier      最终的出参对象 Supplier
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用的目标方法的入参对象 Supplier
	 * @return {@link R}
	 */
	public static <P, H, R> R invoke(Object param, Supplier<R> resultSupplier, Function<P, H> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		H invokeResult = invoke.apply(invokeParam);

		return BeanUtil.copyProperties(invokeResult, resultSupplier.get());
	}

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param               原始入参
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用目标方法的入参类型
	 * @return {@link R}
	 */
	public static <P, R> List<R> invokeList(Object param, Function<P, List<R>> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param               原始入参
	 * @param resultSupplier      最终的出参对象 Supplier
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用的目标方法的入参对象 Supplier
	 * @return {@link List}<{@link R}>
	 */
	public static <P, H, R> List<R> invokeList(Object param, Supplier<R> resultSupplier, Function<P, List<H>> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		List<H> invokeResult = invoke.apply(invokeParam);

		return BeanUtil.copyProperties(invokeResult, resultSupplier);
	}

	/**
	 * 方法调用（入参转化，出参不转化）
	 *
	 * @param param               原始入参
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用的目标方法的入参对象 Supplier
	 * @return {@link PageResp}<{@link R}>
	 */
	public static <P, R> PageResp<R> invokePage(Object param, Function<P, PageResp<R>> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		return invoke.apply(invokeParam);
	}

	/**
	 * 方法调用（出入参转化）
	 *
	 * @param param               原始入参
	 * @param resultSupplier      最终的出参对象 Supplier
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用的目标方法的入参对象 Supplier
	 * @return {@link PageResp}<{@link R}>
	 */
	public static <P, H, R> PageResp<R> invokePage(Object param, Supplier<R> resultSupplier, Function<P, PageResp<H>> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		PageResp<H> invokeResult = invoke.apply(invokeParam);

		return PageResp.of(invokeResult.getTotal(), BeanUtil.copyProperties(invokeResult.getRecords(), resultSupplier));
	}

	// ------------------------------------------------- 有入参，无出参 --------------------------------------------------

	/**
	 * 方法调用（入参转化，无出参）
	 *
	 * @param param               原始入参
	 * @param invoke              目标方法调用函数
	 * @param invokeParamSupplier 调用目标方法的入参类型
	 */
	public static <P> void invoke(Object param, Consumer<P> invoke, Supplier<P> invokeParamSupplier) {
		P invokeParam = BeanUtil.copyProperties(param, invokeParamSupplier.get());

		invoke.accept(invokeParam);
	}

	// ------------------------------------------------- 有出参，无入参 --------------------------------------------------

	/**
	 * 调用
	 * 方法调用（无入参，出参转化）
	 *
	 * @param resultSupplier 最终的出参对象 Supplier
	 * @param invoke         目标方法调用函数
	 * @return {@link R}
	 */
	public static <R> R invoke(Supplier<R> resultSupplier, Supplier<R> invoke) {
		R invokeResult = invoke.get();

		return BeanUtil.copyProperties(invokeResult, resultSupplier.get());
	}

	/**
	 * 调用列表
	 * 方法调用（无入参，出参转化）
	 *
	 * @param resultSupplier 最终的出参对象 Supplier
	 * @param invoke         目标方法调用函数
	 * @return {@link List}<{@link R}>
	 */
	public static <R> List<R> invokeList(Supplier<R> resultSupplier, Supplier<List<R>> invoke) {
		List<R> invokeResult = invoke.get();

		return BeanUtil.copyProperties(invokeResult, resultSupplier);
	}

}
