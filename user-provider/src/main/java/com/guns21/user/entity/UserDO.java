package com.guns21.user.entity;

import com.guns21.common.util.DateUtils;
import com.guns21.support.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_USER")
public class UserDO extends AbstractEntity {
    private String mobile;
    private String realName;
    private String nickName;

    private String idCard;
    private String headerIcon;
    /**
     * 性别:1男2女
     */
    private Byte gender;

    private String summary;
    //用户状态：1正常，2冻结 3禁言
    private Byte state;
    //注册来源： 1 :APP 2:微信 3:微博 4:qq 5:人工
    private Byte source;
    //第三方标识，根据source不同； 微信：openId
    private String thirdId;
    //IP地址
    private String ip;

    private Date lastLoginTime;

    @Transient
    private String password;

    @Override
    public void prePersist() {
        super.prePersist();
        setLastLoginTime(DateUtils.newDate());
    }

    @Override
    public void preUpdate() {
        super.preUpdate();
        setLastLoginTime(DateUtils.newDate());
    }

    /**
     * 用户和第三方交流的唯一标示,可能是电话、openId等
     *
     * @return
     */
    public String getUniqueId() {
        if (null == getMobile()) {
            return this.thirdId;
        }
        return this.mobile;
    }

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

}