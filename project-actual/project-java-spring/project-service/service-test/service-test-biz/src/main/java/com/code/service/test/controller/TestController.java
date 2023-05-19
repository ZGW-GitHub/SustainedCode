package com.code.service.test.controller;

import com.code.framework.basic.result.CommonResult;
import com.code.framework.basic.result.page.PageData;
import com.code.service.test.controller.vo.UserCreateReqVO;
import com.code.service.test.controller.vo.UserPageReqVO;
import com.code.service.test.controller.vo.UserPageRespVO;
import com.code.service.test.service.TestService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("rpc")
	public void rpcTest() {
		EchoService echoService = (EchoService) testService;

		System.err.println(echoService.$echo("test"));
	}

	@PostMapping("save")
	public CommonResult<Long> save(@RequestBody @Valid UserCreateReqVO reqVO) {
		Long recordNo = testService.save(reqVO);

		return CommonResult.success(recordNo);
	}

	@GetMapping("page")
	public CommonResult<PageData<UserPageRespVO>> page(@RequestBody @Valid UserPageReqVO userPageReqVO) {
		// testService.page(userPageReqVO);
		return CommonResult.success(PageData.empty());
	}

}
