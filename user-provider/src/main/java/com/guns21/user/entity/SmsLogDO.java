package com.guns21.user.entity;

import com.guns21.support.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @功能描述:短信日志实体
 * @创建日期:2016-02-01
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_SMS_LOG")
public class SmsLogDO extends AbstractEntity {
    public static final Integer TYPE_REGISTER = 1; //注册类型
    public static final Integer TYPE_LOGIN = 2; //登录类型
    public static final Integer TYPE_ADMIN = 3; //后台管理员

    private String mobile; //手机号码
    private String content; //短信内容
    private Integer type; //短信类型
    private String validCode; //验证码
    private String userId; //用户id
    private String op; //业务操作的id
    private Date pastTime; //过期时间
    private String result; //短信发送结果

    public static SmsLogDO newSmsLog() {
        return new SmsLogDO();
    }
}
