package com.guns21.user.repository.mapper;

import com.guns21.user.entity.SmsLogDO;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;

public interface SmsLogMapper extends BaseMapper<SmsLogDO>,ConditionMapper<SmsLogDO> {

}
