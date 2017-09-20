package com.guns21.user.controller;

import com.guns21.authentication.api.entity.UserRoleDetails;
import com.guns21.result.domain.Result;
import com.guns21.support.controller.AppBaseController;
import com.guns21.web.bind.annotation.CurrentUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jliu on 16/6/30.
 */
@RestController
@RequestMapping("/api/user/v1")
public class UserController extends AppBaseController {

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public Result userInfo(@CurrentUser UserRoleDetails userRoleDetails) {

        return Result.success(userRoleDetails);
    }

}
