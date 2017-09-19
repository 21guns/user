package com.guns21.user.api.enums;

public enum UserType {
    USRE((byte) 0, "一般用户"),
    SPONSOR((byte) 1, "举办人"),
    PUBLISHER((byte) 2, "发布者"),;

    private byte value;
    private String name;

    UserType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }
}
