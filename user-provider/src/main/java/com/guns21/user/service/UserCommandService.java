package com.guns21.user.service;

import com.guns21.result.domain.Result;
import com.guns21.user.api.dto.RegisterUserInfo;
import com.guns21.user.entity.UserDO;

public interface UserCommandService {

    Result register(RegisterUserInfo registerUserInfo);

    Result updateLastLoginTime(String mobile);
}
