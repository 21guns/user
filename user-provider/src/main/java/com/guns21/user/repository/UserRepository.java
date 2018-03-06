package com.guns21.user.repository;

import com.guns21.user.entity.UserDO;

import java.util.Optional;

public interface UserRepository {

    /**
     *
     * @param mobile 手机号
     * @return
     */
    Optional<UserDO> findByMobile(String mobile);

    void save(UserDO userDO);


    void updateLastLoginTime(String mobile);
}
