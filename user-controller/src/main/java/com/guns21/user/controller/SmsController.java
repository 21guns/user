package com.guns21.user.controller;

import com.guns21.common.util.RegexChkUtils;
import com.guns21.result.domain.Result;
import com.guns21.support.controller.AppBaseController;
import com.guns21.user.api.service.ISmsCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/sms/v1")
public class SmsController extends AppBaseController{

    @Autowired
    private ISmsCommandService smsCommandService;

    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    public Result sendRegisterCode(@RequestParam String mobile) throws Exception {

        //检验手机号是否合法
        if (!RegexChkUtils.checkCellPhone(mobile)){
            return resultFactory.fail("user.login.mobile");
        }

        Result result = smsCommandService.sendRegisterCode(mobile);

        return result;
    }
}
