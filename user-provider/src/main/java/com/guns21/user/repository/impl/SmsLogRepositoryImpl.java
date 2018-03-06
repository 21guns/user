package com.guns21.user.repository.impl;

import com.guns21.user.entity.SmsLogDO;
import com.guns21.user.entity.UserDO;
import com.guns21.user.mapper.SmsLogMapper;
import com.guns21.user.repository.SmsLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SmsLogRepositoryImpl implements SmsLogRepository {

    @Autowired
    private SmsLogMapper smsLogMapper;
    @Override
    public void save(SmsLogDO smsLogDO) {
        smsLogMapper.insert(smsLogDO);
    }
}
