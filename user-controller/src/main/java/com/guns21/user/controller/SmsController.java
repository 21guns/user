package com.guns21.user.controller;

import com.guns21.common.util.RegexChkUtils;
import com.guns21.result.domain.Result;
import com.guns21.support.controller.AppBaseController;
import com.guns21.user.service.SmsCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms/v1")
public class SmsController extends AppBaseController{

    @Autowired
    private SmsCommandService smsCommandService;

    @PostMapping(value = "/sendCode")
    public Result sendRegisterCode(@RequestParam String mobile) throws Exception {

        //检验手机号是否合法
        if (!RegexChkUtils.checkCellPhone(mobile)){
            return resultFactory.fail("user.login.mobile");
        }

        Result result = smsCommandService.sendRegisterCode(mobile);

        return result;
    }
}
