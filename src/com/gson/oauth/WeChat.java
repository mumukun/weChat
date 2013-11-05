package com.gson.oauth;

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

    // access_token
    public static String getAccessToken() {
        String appid  = ConfKit.get("appId");
        String secret = ConfKit.get("appSecret");
        String jsonStr = HttpKit.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);
        Map<String, Object> map = Common.jsonToMap(jsonStr);
        return map.get("access_token").toString();
    }
    
    
}
