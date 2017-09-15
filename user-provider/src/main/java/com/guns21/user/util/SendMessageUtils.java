package com.guns21.user.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 发送短信
 * Created by gy on 2016/3/10.
 */
public class SendMessageUtils {
    private static Logger logger = LoggerFactory.getLogger(SendMessageUtils.class);
    private static final String API_KEY = "a81eedff75eb04a40f7d2c69b7ab55f0";

    public static final String SMS_TEMPLATE = "【聚赛体育】您的验证码是";
    public static final String SMS_TEMPLATE_NOTIFY = "【聚赛体育】尊敬的用户：";
    public static final String SMS_TEMPLATE_MOBILE = "，如有疑问请与聚赛App中客服小秘书联系或拨打：400-867-1757。";

    public static boolean send(String mobile, String content) {
        logger.debug("发送短信到:{},内容:{}", mobile, content);
        String resutl = ""; //JavaSmsApi.sendSms(API_KEY, content, mobile);
        Map<String, Object> parse = (Map<String, Object>) JSON.parse(resutl);
        return parse != null && parse.get("code") != null && parse.get("code").equals(0);
    }

    public static Map<String, String> sendBatchSms(String mobile, String content) {
        Map<String, String> codeMap = new HashMap<String, String>();
        logger.debug("发送短信到:{},内容:{}", mobile, content);
        String result = ""; //JavaSmsApi.sendBatchSms(API_KEY, content, mobile);

        JSONObject jsonObj = JSONObject.parseObject(result);
        JSONArray dataResult = jsonObj.getJSONArray("data");
        Iterator<Object> it = dataResult.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            String res = "";
            if (ob.getString("code").equals("0")) {
                res = "成功";
            } else {
                res = "失败";
            }
            codeMap.put(ob.getString("mobile"), res);
        }
        return codeMap;
    }
}

