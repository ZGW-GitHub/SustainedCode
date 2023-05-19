package com.code.service.test.convert;

import com.code.service.test.controller.vo.UserCreateReqVO;
import com.code.service.test.dal.dos.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 愆凡
 * @date 2022/6/12 21:39
 */
@Mapper
public interface UserConvert {

	UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

	UserDO convert(UserCreateReqVO reqVO);

}
