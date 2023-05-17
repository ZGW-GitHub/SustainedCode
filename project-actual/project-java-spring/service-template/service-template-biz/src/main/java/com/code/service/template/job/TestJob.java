package com.code.service.template.job;

import com.code.framework.job.job.abstracts.AbstractSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author 愆凡
 * @date 2022/6/12 21:44
 */
@Slf4j
@Component
public class TestJob extends AbstractSimpleJob<String> {

	@Override
	protected List<String> doFetchDataList() {
		return Collections.singletonList("test");
	}

	@Override
	protected boolean handler(String data) {
		log.debug("------ XXL-JOB Handler ------ 处理数据：{}", data);
		return true;
	}

}
