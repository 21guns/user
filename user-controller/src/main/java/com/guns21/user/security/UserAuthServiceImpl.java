package com.guns21.user.security;

import com.guns21.authentication.api.entity.AuthUser;
import com.guns21.authentication.api.service.UserAuthService;
import com.guns21.authorization.ResourceRoleMapping;
import com.guns21.user.entity.UserDO;
import com.guns21.user.service.UserCommandService;
import com.guns21.web.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
public class UserAuthServiceImpl implements UserAuthService, ResourceRoleMapping {

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private UserCommandService userCommandService;

    @Override
    public AuthUser getUser(String userName) {
        String smsCode = template.opsForValue().get(userName + ".code");
        if (StringUtils.isEmpty(smsCode)) {
            throw new BadCredentialsException("验证码错误");
        }

        userCommandService.saveByMobile(userName);

        AuthUser authUser = new AuthUser();
        authUser.setUserName(userName);
        authUser.setPassword(smsCode);
        return authUser;
    }

    /**
     * 所有登录用户角色都是NORMAL
     * @param userName
     * @return
     */
    @Override
    public List<Role> getUserRoles(String userName) {
        Role role = new Role("NORMAL", "NORMAL", "NORMAL");
        return Arrays.asList(role);
    }

    /**
     * 所有资源NORMAL都可以访问
     * @param resource
     * @return
     */
    @Override
    public List<String> listRole(String resource) {

        return Collections.singletonList("NORMAL");
    }
}
