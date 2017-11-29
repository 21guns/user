package com.guns21.user.service.impl;

import com.guns21.common.util.DateUtils;
import com.guns21.result.domain.Result;
import com.guns21.support.service.BaseCommandService;
import com.guns21.user.UserConstant;
import com.guns21.user.entity.SmSTypeEnum;
import com.guns21.user.entity.SmsLogDO;
import com.guns21.user.service.SmsCommandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SmsCommandServiceImpl extends BaseCommandService implements SmsCommandService {
    private static Logger logger = LoggerFactory.getLogger(SmsCommandServiceImpl.class);

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    EventBus eventBus;

    @Override
    public Result sendRegisterCode(String mobile) {
        ValueOperations<String, String> ops = template.opsForValue();
        String strSmsCount = mobile + ".smsCount";
        String strSmsCode = mobile + ".code";
        //每天限制发送验证码次数
        int maxCount = 3;
        if (ops.get(strSmsCount) != null && Integer.valueOf(ops.get(strSmsCount)) >= maxCount) {
            return Result.fail("您今天获取验证码过于频繁,请休息一下明天再试.");
        }
        ;
        String validCode = (String) ops.get(strSmsCode);
        if (validCode == null) {
            SmsLogDO smsLogDO = SmsLogDO.newSmsLog();
            smsLogDO.setType(SmSTypeEnum.LOGIN.getCode());
            smsLogDO.setMobile(mobile);
            Date currentDate = DateUtils.newDate();
            smsLogDO.setCreateTime(currentDate);
            //设置过期时间为1分钟
            Date pastTime = new Date(currentDate.getTime() + (60 * 1000));
            smsLogDO.setPastTime(pastTime);
            smsLogDO.setValidCode(generatorCode(4));
            if (StringUtils.isEmpty(smsLogDO.getContent())) {
                smsLogDO.setContent(UserConstant.SMS_TEMPLATE + smsLogDO.getValidCode());
            }
            ops.set(strSmsCode, smsLogDO.getValidCode(), 1, TimeUnit.MINUTES);

            eventBus.notify(UserConstant.SMS_SEND_EVENT, Event.wrap(smsLogDO));

//            persistedActorRef.tell(smsLogDO, null); TODO 数据持久化

            Long smsCount = ops.increment(strSmsCount, 1);
            if (smsCount == 1) {
                int expirationTime = DateUtils.datediffMinute(DateUtils.getEndDate(), DateUtils.newDate());
                template.expire(strSmsCount, expirationTime, TimeUnit.MINUTES);
            }

            logger.debug("{}已发送{}次验证码", mobile, smsCount);


        } else {
            return Result.fail("验证码已发送,请稍后再试.");
        }
        return Result.success();
    }

    private static String generatorCode(int length) {
        Random random = new Random();
        String collect = random.ints(length, 0, 9).mapToObj(Integer::toString).collect(Collectors.joining(""));
        return collect;
    }
}

