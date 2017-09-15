package com.guns21.user.entity;

/**
 */
public enum SmSTypeEnum {

    REGISTER(1, "注册"),
    VERIFY(2, "用户认证"),
    LOGIN(3, "用户登录"),
    EXPIRATION(4, "EXP_TIME");


    private int code;
    private String name;

    SmSTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        for (SmSTypeEnum status : SmSTypeEnum.values()) {
            if (status.getCode() == code) {
                return status.name;
            }
        }
        return "";
    }

    public static Boolean isValid(int code) {
        for (SmSTypeEnum status : SmSTypeEnum.values()) {
            if (status.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
