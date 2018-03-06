package com.guns21.user.mapper;

import com.guns21.user.entity.SmsLogDO;
import com.guns21.user.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;

import java.time.LocalDateTime;

public interface SmsLogMapper extends BaseMapper<SmsLogDO>,ConditionMapper<SmsLogDO> {

}
