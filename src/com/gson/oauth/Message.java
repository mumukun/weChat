package com.gson.oauth;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

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
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public String sendText(String accessToken,String openId, String text) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String reslut = HttpKit.post(MESSAGE_URL.concat(accessToken), "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + text + "\"}}");
        return reslut;
    }
}
