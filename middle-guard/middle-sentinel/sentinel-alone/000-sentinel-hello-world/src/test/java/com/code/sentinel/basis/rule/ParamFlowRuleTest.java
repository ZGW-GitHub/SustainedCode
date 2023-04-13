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

package com.code.sentinel.basis.rule;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParamFlowRuleTest {

	private final String paramA = "paramA";
	private final String paramB = "paramB";

	private final ExecutorService service = Executors.newFixedThreadPool(10);

	@Test
	@SneakyThrows
	void demoTest() {
		// 1、定义资源
		String resource = "HelloWorld";

		// 2、配置规则
		configRules(resource);

		for (int i = 0; i < 10; i++) {
			service.submit(() -> {
				for (int j = 0; j < 100; j++) {
					if (SphO.entry(resource, EntryType.IN, 1, paramA, paramB)) {
						try {
							// 被保护的逻辑
							System.out.println("hello world ! ");
						} finally {
							SphO.exit(3, paramA, paramB);
						}
					} else {
						// 被流控的逻辑
						System.out.println("blocked ! ");
					}
				}
			});
		}

		TimeUnit.SECONDS.sleep(10);
	}

	void configRules(String resource) {
		ParamFlowRule flowRule = makeRule(resource);

		ParamFlowRuleManager.loadRules(Collections.singletonList(flowRule));
	}

	ParamFlowRule makeRule(String resource) {
		ParamFlowRule rule = new ParamFlowRule();
		rule.setResource(resource);
		rule.setCount(5);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

		// 针对 String 类型的参数 paramA，单独设置限流 QPS 阈值为 3，而不是全局的阈值 5.
		ParamFlowItem item = new ParamFlowItem().setObject(paramA).setClassType(String.class.getName()).setCount(3);
		rule.setParamFlowItemList(Collections.singletonList(item));

		return rule;
	}

}
