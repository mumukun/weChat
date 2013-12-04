package com.gson.oauth;

import com.gson.util.HttpKit;

/**
 * 客服消息接口
 * @author L.cm
 * @date 2013-11-5 下午3:32:30
 */
public class Message {

    private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
    
    // {"touser":"OPENID","msgtype":"text","text":{"content":"Hello World"}}
    public static void send(){
        String accessToken = WeChat.getAccessToken();
        System.out.println(accessToken);
        String ss = HttpKit.post(MESSAGE_URL.concat(accessToken), "{\"touser\":\"oOGf-jgjmwxFVU66D-IFO2AFK8ic\",\"msgtype\":\"text\",\"text\":{\"content\":\"Hello World\"}}");
        System.out.println(ss);
    }
    
    
    
    public static void main(String[] args) {
//        TextOutMessage t = new TextOutMessage();
//        t.setContent("adsfsadf");
//        t.setFuncFlag(0);
//        System.out.println(JSON.toJSONString(t, false));
        Message.send();
    }
    
}
