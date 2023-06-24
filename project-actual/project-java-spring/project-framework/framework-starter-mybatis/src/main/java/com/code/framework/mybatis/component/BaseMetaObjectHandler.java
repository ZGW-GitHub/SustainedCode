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

package com.code.framework.mybatis.component;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.code.framework.basic.trace.context.TraceContextHelper;
import com.code.framework.basic.trace.context.TraceContextKeyEnum;
import com.code.framework.basic.trace.context.TraceUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Snow
 * @date 2023/6/13 21:10
 */
@Slf4j
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.debug("start insert fill ....");

		TraceUserInfo userInfo = JSONUtil.toBean(TraceContextHelper.getInfo(TraceContextKeyEnum.USER_INFO), TraceUserInfo.class);

		this.strictInsertFill(metaObject, "creator", userInfo::getUserId, String.class);
		this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class); // 起始版本 3.3.3(推荐)

		// this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
		// this.fillStrategy(metaObject, "createTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.debug("start update fill ....");

		TraceUserInfo userInfo = JSONUtil.toBean(TraceContextHelper.getInfo(TraceContextKeyEnum.USER_INFO), TraceUserInfo.class);

		this.strictInsertFill(metaObject, "updater", userInfo::getUserId, String.class);
		this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)

	}

}
