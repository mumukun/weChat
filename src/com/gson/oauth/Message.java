package com.gson.oauth;

import com.gson.WeChat;
import com.gson.util.HttpKit;

/**
 * 客服消息接口
 *
 * @author L.cm
 * @date 2013-11-5 下午3:32:30
 */
public class Message {

    private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    /**
     * 发送文本客服消息
     * @param openId
     * @param text
     */
    public static String sendText(String openId, String text) {
        String accessToken = WeChat.getAccessToken();
        String reslut = HttpKit.post(MESSAGE_URL.concat(accessToken), "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + "\"}}");
        return reslut;
    }
}
