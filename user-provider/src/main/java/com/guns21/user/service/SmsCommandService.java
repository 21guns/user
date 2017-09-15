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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsCommandService extends BaseCommandService implements ISmsCommandService {

    @Autowired
    private RedisTemplate<String, String> template;

    @Override
    public Result sendRegisterCode(String mobile) {
        //每天限制发送验证码次数
        int maxCount = 3;
        if (cacheRedis.get(getExpirationTime(mobile)) != null) {
            int sms = cacheRedis.get(getExpirationTime(mobile));
            if (sms >= Integer.parseInt(configData.getConfigValues())) {
                return Result.fail("您今天获取验证码过于频繁,请休息一下明天再试.");
            }
        }
        template.opsForValue().set();

        String loginKey = mobile + ":" + SmSTypeEnum.LOGIN.getCode();
        String validCode = cacheRedis.get(loginKey);
        if (validCode == null) {
            SmsLog smsLog = SmsLog.newSmsLog();
            smsLog.setId(ID.get());
            smsLog.setType(SmSTypeEnum.LOGIN.getCode());
            smsLog.setMobile(mobile);
            Date currentDate = DateUtils.newDate();
            smsLog.setCreateTime(currentDate);
            Date pastTime = new Date(currentDate.getTime() + (60 * 1000));//设置过期时间为1分钟
            smsLog.setPastTime(pastTime);
            smsLog.setValidCode("dddd"); //TODO 随机验证码
            cacheRedis.setOnSeconds(smsLog.getMobile() + ":" + SmSTypeEnum.LOGIN.getCode(), smsLog.getValidCode(), 60); //将当前的短信放redis中
            if (StringUtils.isEmpty(smsLog.getContent())) {
                smsLog.setContent(SendMessageUtils.SMS_TEMPLATE + smsLog.getValidCode());
            }
            persistedActorRef.tell(smsLog, null);

            Long smsCount = cacheRedis.increment(getExpirationTime(mobile), 1);
            logger.debug("你已发送{}次验证码", smsCount);
            if (smsCount == 1) {
                int expirationTime = DateUtils.datediffMinute(DateUtils.getEndDate(), DateUtils.newDate());
                cacheRedis.expire(getExpirationTime(mobile), expirationTime * 60);
            }


        } else {
            return Result.fail("验证码已发送,请稍后再试.");
        }
        return Result.success();
    }
}

