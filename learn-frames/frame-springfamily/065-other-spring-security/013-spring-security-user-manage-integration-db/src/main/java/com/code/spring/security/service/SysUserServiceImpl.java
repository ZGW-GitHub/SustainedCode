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

package com.code.spring.security.service;

import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.code.spring.security.dal.dos.SysUser;
import com.code.spring.security.dal.mapper.SysUserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Snow
 * @date 2022/10/17 09:59
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserMapper sysUserMapper;

	@Override
	public SysUser findByAccount(String account) {
		return ChainWrappers.lambdaQueryChain(sysUserMapper).eq(SysUser::getAccount, account).orderByDesc(SysUser::getId).last("limit 1").one();
	}

}
