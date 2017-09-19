package com.guns21.user.weixin.config;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jliu on 16/8/5.
 */
@ConfigurationProperties(prefix = "com.guns21.weixin")
public class WxConfig extends WxMpInMemoryConfigStorage {
    protected String notifyUrl;

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
