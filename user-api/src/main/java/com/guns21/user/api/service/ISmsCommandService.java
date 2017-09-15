package com.guns21.user.api.service;

import com.guns21.result.domain.Result;

public interface ISmsCommandService {

    Result<String> sendRegisterCode(String mobile);
}
