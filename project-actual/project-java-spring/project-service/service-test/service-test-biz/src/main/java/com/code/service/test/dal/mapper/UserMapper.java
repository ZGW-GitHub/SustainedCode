package com.code.service.test.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.service.test.dal.dos.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 愆凡
 * @date 2022/6/12 18:41
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
