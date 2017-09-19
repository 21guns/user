package com.guns21.user.api.enums;

public enum UserSource {
    APP((byte) 1, "app"),
    WEIXIN((byte) 2, "微信"),
    WEIBO((byte) 3, "微博"),
    QQ((byte) 4, "qq"),
    MANUAL((byte) 5, "手动添加");

    private byte value;
    private String name;

    UserSource(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}


