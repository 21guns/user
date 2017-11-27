package com.guns21.user.repository;

import com.guns21.user.entity.SmsLogDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SmsLogRepository extends JpaRepository<SmsLogDO, String>, JpaSpecificationExecutor<SmsLogDO> {

}
