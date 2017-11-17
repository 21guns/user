package com.guns21.user.security;

import com.guns21.authentication.api.entity.UserRoleDetails;
import com.guns21.authentication.api.service.UserAuthService;
import com.guns21.common.util.ObjectUtils;
import com.guns21.servlet.util.RequestUtils;
import com.guns21.user.api.dto.RegisterUserInfo;
import com.guns21.user.api.enums.UserSource;
import com.guns21.web.entity.Role;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

public class ThirdLoginAuthenticationProvider implements AuthenticationProvider {
    private static Logger logger = LoggerFactory.getLogger(ThirdLoginAuthenticationProvider.class);

    private RetryPolicy retryPolicy;
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    protected UserAuthService userAuthService;

    @PostConstruct
    private void init() {
        retryPolicy = new RetryPolicy()
                .retryWhen(null)
                .withMaxRetries(2); //重试3次
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String code = request.getParameter("code");
        Byte source =  Byte.valueOf(request.getParameter("source"));
        if (StringUtils.isEmpty(code) || null == source) {
            throw  new BadCredentialsException("code source 为空");
        }
        if (UserSource.WEIXIN.getValue() == source) {
            //微信
            RegisterUserInfo registerUserInfo = Failsafe.with(retryPolicy).get(() -> {
                try {
                    WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                    WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
                    RegisterUserInfo newRegisterUserInfo = RegisterUserInfo.newRegisterUserInfo();
                    newRegisterUserInfo.setNickName(wxMpUser.getNickname());
                    newRegisterUserInfo.setGender(BigInteger.valueOf(wxMpUser.getSexId()).byteValueExact());
                    newRegisterUserInfo.setHeaderIcon(wxMpUser.getHeadImgUrl());
                    newRegisterUserInfo.setSource(source);
                    newRegisterUserInfo.setThirdId(wxMpUser.getOpenId());
                    //取openId后六位作为密码
                    newRegisterUserInfo.setPassword(wxMpUser.getOpenId().substring(wxMpUser.getOpenId().length() - 6));
                    newRegisterUserInfo.setIp(RequestUtils.getIPBy(request));

                    return newRegisterUserInfo;
                } catch (WxErrorException e) {
                    logger.error("调用微信授权接口异常,重试调用",e);
                    return null;
                }
            });
            if (null == registerUserInfo) {
                logger.error("调用微信授权接口异常");
                throw  new BadCredentialsException("错误的三方登录类型");
            }
            //生成认证对象
            List<Role> roles = userAuthService.getUserRoles(registerUserInfo.getNickName());
            List<GrantedAuthority> grantedAuthorities = null;
            if (ObjectUtils.nonEmpty(roles)) {
                grantedAuthorities = roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
            }

            UserRoleDetails userRoleDetails = new UserRoleDetails(registerUserInfo.getNickName(), registerUserInfo.getPassword(), grantedAuthorities);
            userRoleDetails.setUserId(registerUserInfo.getThirdId());
            userRoleDetails.setNickname(registerUserInfo.getNickName());
            userRoleDetails.setRoles(roles);

            Authentication auth = new UsernamePasswordAuthenticationToken(userRoleDetails, userRoleDetails.getPassword(), userRoleDetails.getAuthorities());

            return auth;
        } else {
            logger.error("错误的三方登录类型[{}]", source);
            throw  new BadCredentialsException("错误的三方登录类型");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
