package com.gson.oauth;

import java.util.Map;

import com.gson.util.Common;
import com.gson.util.HttpKit;

/**
 * 微信用户api
 * @author L.cm
 * @date 2013-11-6 下午3:04:41
 */
public class User {

    // https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID
    
    public static Map<String, Object> getInfo(String openid) {
        String accessToken = WeChat.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid" + openid;
        String jsonStr = HttpKit.get(url);
        return Common.jsonToMap(jsonStr);
    }
}
