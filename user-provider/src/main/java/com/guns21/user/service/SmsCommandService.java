package com.guns21.user.service;

import com.guns21.common.util.DateUtils;
import com.guns21.common.uuid.ID;
import com.guns21.result.domain.Result;
import com.guns21.support.service.BaseCommandService;
import com.guns21.user.api.service.ISmsCommandService;
import com.guns21.user.entity.SmSTypeEnum;
import com.guns21.user.entity.SmsLog;
import com.guns21.user.util.SendMessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SmsCommandService extends BaseCommandService implements ISmsCommandService {
    private static Logger logger = LoggerFactory.getLogger(SmsCommandService.class);

    @Autowired
    private RedisTemplate<String, String> template;

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
            SmsLog smsLog = SmsLog.newSmsLog();
            smsLog.setId(ID.get());
            smsLog.setType(SmSTypeEnum.LOGIN.getCode());
            smsLog.setMobile(mobile);
            Date currentDate = DateUtils.newDate();
            smsLog.setCreateTime(currentDate);
            Date pastTime = new Date(currentDate.getTime() + (60 * 1000)); //设置过期时间为1分钟
            smsLog.setPastTime(pastTime);
            smsLog.setValidCode(generatorCode(4));
            if (StringUtils.isEmpty(smsLog.getContent())) {
                smsLog.setContent(SendMessageUtils.SMS_TEMPLATE + smsLog.getValidCode());
            }
            ops.set(strSmsCode, smsLog.getValidCode(), 1, TimeUnit.MINUTES);


//            persistedActorRef.tell(smsLog, null); TODO 数据持久化

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

