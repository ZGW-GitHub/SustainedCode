package com.code.service.test.controller;

import com.code.framework.basic.result.CommonResult;
import com.code.framework.basic.result.page.PageData;
import com.code.service.test.controller.vo.UserCreateReqVO;
import com.code.service.test.controller.vo.UserPageReqVO;
import com.code.service.test.controller.vo.UserPageRespVO;
import com.code.service.test.convert.UserConvert;
import com.code.service.test.service.TestService;
import com.code.service.test.service.model.UserCreateReqModel;
import com.code.service.test.service.model.UserPageReqModel;
import com.code.service.test.service.model.UserPageRespModel;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 愆凡
 * @date 2022/6/12 18:27
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

	@Resource
	private TestService testService;

	@PostMapping("save")
	public CommonResult<Long> save(@RequestBody @Valid UserCreateReqVO reqVO) {
		UserCreateReqModel userCreateReqModel = UserConvert.INSTANCE.voToModel(reqVO);
		Long               recordNo           = testService.save(userCreateReqModel);

		return CommonResult.success(recordNo);
	}

	@PostMapping("page")
	public CommonResult<PageData<UserPageRespVO>> page(@RequestBody @Valid UserPageReqVO userPageReqVO) {
		UserPageReqModel            userPageReqModel = UserConvert.INSTANCE.voToModel(userPageReqVO);
		PageData<UserPageRespModel> pageData         = testService.page(userPageReqModel);

		return CommonResult.success(PageData.of(pageData.getTotal(), UserConvert.INSTANCE.modelToVo(pageData.getRecords())));
	}

}
