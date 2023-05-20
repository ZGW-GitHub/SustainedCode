package com.code.service.test.convert;

import com.code.service.test.controller.vo.UserCreateReqVO;
import com.code.service.test.controller.vo.UserPageReqVO;
import com.code.service.test.controller.vo.UserPageRespVO;
import com.code.service.test.dal.dos.UserDO;
import com.code.service.test.service.model.UserCreateReqModel;
import com.code.service.test.service.model.UserPageReqModel;
import com.code.service.test.service.model.UserPageRespModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/12 21:39
 */
@Mapper
public interface UserConvert {

	UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

	UserCreateReqModel voToModel(UserCreateReqVO userCreateReqVO);

	UserDO modelToDo(UserCreateReqModel userCreateReqModel);

	UserPageReqModel voToModel(UserPageReqVO userPageReqVO);

	UserDO modelToDo(UserPageReqModel userPageReqModel);

	UserPageRespModel doToModel(UserDO userDO);

	List<UserPageRespModel> doToModel(List<UserDO> userDOList);

	UserPageRespVO modelToVo(UserPageRespModel userPageRespMode);

	List<UserPageRespVO> modelToVo(List<UserPageRespModel> userPageRespModelList);

}
