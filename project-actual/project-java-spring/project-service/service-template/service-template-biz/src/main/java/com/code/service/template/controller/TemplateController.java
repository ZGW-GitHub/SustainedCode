package com.code.service.template.controller;

import com.code.framework.basic.result.CommonResult;
import com.code.service.template.controller.vo.TemplateCreateReqVO;
import com.code.service.template.service.TemplateService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.web.bind.annotation.*;

/**
 * @author 愆凡
 * @date 2022/6/12 18:27
 */
@Slf4j
@RestController
@RequestMapping("template")
public class TemplateController {

	@Resource
	private TemplateService templateService;

	@GetMapping("test")
	public String test() {
		return "tt";
	}

	@PostMapping("rpc")
	public void rpcTest() {
		EchoService echoService = (EchoService) templateService;

		System.err.println(echoService.$echo("test"));
	}

	@PostMapping("save")
	public CommonResult<Long> save(@RequestBody TemplateCreateReqVO reqVO) {
		Long recordNo = templateService.save(reqVO);

		return CommonResult.success(recordNo);
	}

}
