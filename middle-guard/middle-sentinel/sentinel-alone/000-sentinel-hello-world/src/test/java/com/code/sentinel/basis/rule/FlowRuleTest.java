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

import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class FlowRuleTest {

	@Test
	void simpleTest() {
		// 1、定义资源
		String resource = "HelloWorld";

		// 2、配置规则
		configRules(resource);

		int count = 1;
		while (count <= 100) {
			if (SphO.entry(resource, 1)) {
				try {
					// 被保护的逻辑
					System.out.println("hello world ! " + count);
				} finally {
					SphO.exit(1);
				}
			} else {
				// 被流控的逻辑
				System.out.println("blocked ! " + count);
			}
			count++;
		}
	}

	void configRules(String resource) {
		FlowRule flowRule = makeRule(resource);

		FlowRuleManager.loadRules(Collections.singletonList(flowRule));
	}

	FlowRule makeRule(String resource) {
		FlowRule rule = new FlowRule();
		rule.setResource(resource);
		rule.setCount(20);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		return rule;
	}

}
