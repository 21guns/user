package com.guns21.user.controller;

import com.guns21.support.controller.AppBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/sms/v1")
public class SmsController extends AppBaseController{

//    @RequestMapping(value = "/sendCode",method = RequestMethod.POST)
//    public Result sendRegisterCode(@RequestParam String mobile,
//                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        //检验手机号是否合法
//        if(!RegexChkUtils.checkCellPhone(mobile)){
//            return resultFactory.fail("user.login.mobile");
//        }
//
//        Result result = userCommandService.sendRegisterCode(mobile);
//
//        return result;
//    }
}
