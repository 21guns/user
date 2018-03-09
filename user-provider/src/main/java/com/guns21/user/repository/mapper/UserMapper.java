package com.guns21.user.repository.mapper;

import com.guns21.user.entity.UserDO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;

import java.time.LocalDateTime;

public interface UserMapper extends BaseMapper<UserDO>,ConditionMapper<UserDO> {

    void updateLastLoginTime(@Param("mobile") String mobile, @Param("loginTime") LocalDateTime loginTime);
}
