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
    public Result updateLastLoginTime(String mobile) {
        Optional<UserDO> exist = userRepository.findByMobile(mobile);
        exist.ifPresent(userDO -> {
            userRepository.updateLastLoginTime(mobile);
        });

        return  exist.isPresent()?Result.success(exist.get()):Result.fail("mobile don't exist");
    }
}
