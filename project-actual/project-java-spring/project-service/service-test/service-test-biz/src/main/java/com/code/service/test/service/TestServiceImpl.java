package com.code.service.test.service;

import com.code.service.test.controller.vo.UserCreateReqVO;
import com.code.service.test.convert.UserConvert;
import com.code.service.test.dal.dos.UserDO;
import com.code.service.test.dal.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	public Long save(UserCreateReqVO reqVO) {
		UserDO userDO = UserConvert.INSTANCE.convert(reqVO);
		userMapper.insert(userDO);

		return userDO.getRecordNo();
	}

}
