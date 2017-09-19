package com.guns21.user.api.dto;

import com.guns21.user.api.UserConstants;
import com.guns21.user.api.enums.UserSource;
import com.guns21.user.api.enums.UserType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class RegisterUserInfo {
    @Pattern(regexp = UserConstants.MOBILE_REGEXP, message = "{user.mobile.regxp}")
    @NotNull
    private String mobile;
    @Pattern(regexp = UserConstants.PASSWORD_REGEXP, message = "{user.password.regxp}")
    private String password;
    private Date registerAt;
    @NotNull
    private String nickName;
    private Byte gender;
    private String headerIcon;
    @NotNull
    private String registerCode;
    private String ip;
    private Byte type = UserType.USRE.getValue(); //用户类型
    private Byte source = UserSource.APP.getValue(); //注册来源： 1 :APP 2:微信 3:微博 4:qq
    private String thirdId; //第三方标识，根据source不同； 微信：openId/

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Byte getSource() {
        return source;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public String getHeaderIcon() {
        return headerIcon;
    }

    public void setHeaderIcon(String headerIcon) {
        this.headerIcon = headerIcon;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }


    public static RegisterUserInfo newRegisterUserInfo() {
        return new RegisterUserInfo();
    }

}
