package com.guns21.user.service.impl;

import com.guns21.result.domain.Result;
import com.guns21.user.api.dto.RegisterUserInfo;
import com.guns21.user.entity.UserDO;
import com.guns21.user.repository.UserRepository;
import com.guns21.user.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Result register(RegisterUserInfo registerUserInfo) {
        return null;
    }

    @Override
    public Result<UserDO> saveByMobile(String mobile) {
        Optional<UserDO> exist = userRepository.findByMobile(mobile);
        UserDO userDO = exist.orElse(UserDO.builder().mobile(mobile).build());
        userDO.preUpdate();
        UserDO save = userRepository.save(userDO);
        return Result.success(save);
    }
}
