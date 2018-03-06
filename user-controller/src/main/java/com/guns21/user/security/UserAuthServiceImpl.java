package com.guns21.user.security;

import com.guns21.authentication.api.entity.AuthUser;
import com.guns21.authentication.api.service.UserAuthService;
import com.guns21.authorization.ResourceRoleMapping;
import com.guns21.common.util.ObjectUtils;
import com.guns21.result.domain.Result;
import com.guns21.user.entity.UserDO;
import com.guns21.user.service.UserCommandService;
import com.guns21.web.entity.Role;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 */
@Service
public class UserAuthServiceImpl implements UserAuthService, ResourceRoleMapping {
    private static Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private List<String> authUrls;

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private UserCommandService userCommandService;

    @Value("${com.guns21.security.auth-url:#{null}}")
    public void setAuthUrls(String[] authUrls) {
        if (Objects.nonNull(authUrls)) {
            this.authUrls  = Arrays.asList(authUrls);
        } else {
            this.authUrls = Collections.EMPTY_LIST;
        }
    }

    @Override
    public AuthUser getUser(String userName) {
        String smsCode = template.opsForValue().get(userName + ".code");
        if (StringUtils.isEmpty(smsCode)) {
            throw new BadCredentialsException("验证码过期！");
        }

        Result<UserDO> userDOResult = userCommandService.updateLastLoginTime(userName);

        if (userDOResult.getSuccess()) {
            AuthUser authUser = new AuthUser();
            authUser.setUserName(userName);
            authUser.setPassword(smsCode);
            authUser.setId(userDOResult.getData().getId());
            return authUser;
        } else {
            logger.error("登录异常:{}", userDOResult.getMessage());
            throw new BadCredentialsException("登录异常");
        }
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
        if (ObjectUtils.nonEmpty(authUrls) && authUrls.contains(resource)) {
            return Collections.singletonList("NORMAL");
        }
        return Collections.singletonList("ROLE_ANONYMOUS");
    }
}
