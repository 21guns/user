package com.guns21.user.service;

import com.guns21.result.domain.Result;

public interface SmsCommandService {

    Result<String> sendRegisterCode(String mobile);
}
