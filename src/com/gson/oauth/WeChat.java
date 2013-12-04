package com.gson.oauth;

import java.util.HashMap;
import java.util.Map;

import com.gson.util.Common;
import com.gson.util.ConfKit;
import com.gson.util.HttpKit;

/**
 * 微信的常用的请求
 * @author L.cm
 * @date 2013-11-5 下午3:01:20
 */
public class WeChat {
    
    private static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";

    /**
     * 获取access_token
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getAccessToken() {
        String appid  = ConfKit.get("AppId");
        String secret = ConfKit.get("AppSecret");
        String jsonStr = HttpKit.get(ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
        Map<String, Object> map = Common.jsonToMap(jsonStr);
        return map.get("access_token").toString();
    }
    
    // 投诉处理
    public static boolean payfeedback(String openid, String feedbackid) {
        Map<String, String> map = new HashMap<String, String>();
        String accessToken = getAccessToken();
        map.put("access_token", accessToken);
        map.put("openid", openid);
        map.put("feedbackid", feedbackid);
        String jsonStr = HttpKit.get(PAYFEEDBACK_URL, map);
        Map<String, Object> jsonMap = Common.jsonToMap(jsonStr);
        return "0".equals(jsonMap.get("errcode").toString());
    }
    
}
