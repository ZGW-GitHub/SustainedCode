package com.code.service.test.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.code.service.test.dal.dos.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Objects;

/**
 * @author 愆凡
 * @date 2022/6/12 18:41
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {

	default Page<UserDO> page(UserDO userDO, long currentPage, long pageSize) {
		return ChainWrappers.lambdaQueryChain(this)
				.eq(Objects.nonNull(userDO.getAge()), UserDO::getAge, userDO.getAge())
				.orderByDesc(UserDO::getRecordNo)
				.page(Page.of(currentPage, pageSize));
	}

}
