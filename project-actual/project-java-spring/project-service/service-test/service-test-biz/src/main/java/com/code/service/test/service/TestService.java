package com.code.service.test.service;

import com.code.framework.basic.result.page.PageResp;
import com.code.service.test.service.model.UserCreateReqModel;
import com.code.service.test.service.model.UserPageReqModel;
import com.code.service.test.service.model.UserPageRespModel;

/**
 * @author 愆凡
 * @date 2022/6/12 18:15
 */
public interface TestService {

	Long save(UserCreateReqModel reqModel);

	PageResp<UserPageRespModel> page(UserPageReqModel userPageReqModel);

}
