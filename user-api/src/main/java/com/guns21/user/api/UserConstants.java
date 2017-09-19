package com.guns21.user.api;

public class UserConstants {

    /**
     * 正则表达式
     */
    public static final String UUID_REGEX_32 = "[0-9a-z]{32}";
    public static final String UUID_REGEX_24 = "[0-9a-z]{24}";
    public static final String MOBILE_REGEXP = "^[1]([3,5,7,8][0-9]{1}|59|58|88|89)[0-9]{8}$";
    public static final String PASSWORD_REGEXP = "^[A-Za-z0-9\\^\\$\\.\\+\\*_@!#%&~=-]{6,32}$";
    public static final String ID_CARD_REGEXP = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
}
