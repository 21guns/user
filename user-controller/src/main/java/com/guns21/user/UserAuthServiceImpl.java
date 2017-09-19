package com.guns21.user;

import com.guns21.authentication.api.entity.AuthUser;
import com.guns21.authentication.api.entity.Role;
import com.guns21.authentication.api.service.UserAuthService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
@Service
public class UserAuthServiceImpl implements UserAuthService{
    @Override
    public AuthUser getUser(String s) {
        AuthUser authUser = new AuthUser();
        authUser.setUserName(s);
        return authUser;
    }

    @Override
    public List<Role> getUserRoles(String s) {
        Role role = new Role();
        role.setName("normal");
        return Arrays.asList(role);
    }
}
