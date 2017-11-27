package com.guns21.user.entity;


import com.guns21.common.helper.PasswordHelper;
import com.guns21.support.entity.BaseIDEntity;
import lombok.Data;

/**
 * @功能描述:用户授权信息实体
 * @创建日期:2016-01-26
 */
@Data
public class Authorization extends BaseIDEntity {
    /**
     * 密码Hash
     */
    private String passwordHash;
    /**
     * 用户ID
     */
    private String userId;
    private String passwordSalt;


    public boolean checkPassword(String mobile, String tempPass) {

        String tempHash = PasswordHelper.encodePassword(mobile, tempPass, this.getPasswordSalt());
        if (tempHash.equals(this.getPasswordHash())) {
            return true;
        } else {
            return false;
        }
    }

    public void changePassword(String mobile, String newPass) {

        this.setPasswordSalt(PasswordHelper.generateSalt());
        this.setPasswordHash(PasswordHelper.encodePassword(mobile, newPass, this.getPasswordSalt()));

//        this.setPasswordHash(PasswordHelper.genHash(newPass));
    }

    public static Authorization newAuthorization() {
        return new Authorization();
    }


    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public static Authorization newAuthorization(String usernamen, String password) {
        Authorization userAuth = new Authorization();
        userAuth.setPasswordSalt(PasswordHelper.generateSalt());
        userAuth.setPasswordHash(PasswordHelper.encodePassword(usernamen, password, userAuth.getPasswordSalt()));

//        userAuth.setPasswordHash(PasswordHelper.genHash(password));
        return userAuth;
    }


}
