package com.code.service.test.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.code.framework.basic.result.page.PageData;
import com.code.service.test.convert.UserConvert;
import com.code.service.test.dal.dos.UserDO;
import com.code.service.test.dal.mapper.UserMapper;
import com.code.service.test.service.model.UserCreateReqModel;
import com.code.service.test.service.model.UserPageReqModel;
import com.code.service.test.service.model.UserPageRespModel;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 愆凡
 * @date 2022/6/12 18:34
 */
@Slf4j
@Setter
@Service
public class TestServiceImpl implements TestService {

	@Resource
	private UserMapper userMapper;

	@Override
	public Long save(UserCreateReqModel reqModel) {
		UserDO userDO = UserConvert.INSTANCE.modelToDo(reqModel);
		userDO.setRecordNo(RandomUtil.randomLong(Long.MAX_VALUE));
		userDO.setCreateTime(DateUtil.date());
		userMapper.insert(userDO);

		return userDO.getRecordNo();
	}

	@Override
	public PageData<UserPageRespModel> page(UserPageReqModel userPageReqModel) {
		UserDO userDO = UserConvert.INSTANCE.modelToDo(userPageReqModel);

		Page<UserDO> userDOPage = userMapper.page(userDO, userPageReqModel.currentPage(), userPageReqModel.pageSize());

		return PageData.of(userDOPage.getTotal(), UserConvert.INSTANCE.doToModel(userDOPage.getRecords()));
	}

}
