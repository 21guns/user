package com.guns21.user.controller;

import com.guns21.authentication.api.entity.UserRoleDetails;
import com.guns21.result.domain.Result;
import com.guns21.support.controller.AppBaseController;
import com.guns21.user.api.dto.RegisterUserInfo;
import com.guns21.web.bind.annotation.CurrentUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by jliu on 16/6/30.
 */
@RestController
@RequestMapping("/api/v1/user/")
public class UserController extends AppBaseController {

    @GetMapping(value = "user")
    public Result userInfo(@CurrentUser UserRoleDetails userRoleDetails) {

        return Result.success(userRoleDetails);
    }

}
