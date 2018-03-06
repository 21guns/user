package com.guns21.user.repository.impl;

import com.guns21.user.entity.UserDO;
import com.guns21.user.mapper.UserMapper;
import com.guns21.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Optional<UserDO> findByMobile(String mobile) {

        return Optional.ofNullable(userMapper.selectOne(UserDO.builder().mobile(mobile).build()));
    }

    @Override
    public void save(UserDO userDO) {
        userMapper.insert(userDO);
    }

    @Override
    public void updateLastLoginTime(String mobile) {
        userMapper.updateLastLoginTime(mobile, LocalDateTime.now());
    }
}
