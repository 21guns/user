package com.guns21.user.repository;

import com.guns21.user.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, String>, JpaSpecificationExecutor<UserDO> {

    /**
     *
     * @param mobile 手机号
     * @return
     */
    Optional<UserDO> findByMobile(String mobile);

}
