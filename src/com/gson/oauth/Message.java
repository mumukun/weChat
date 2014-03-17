/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.oauth;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
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
     * 发送客服消息
     * @param accessToken
     * @param message
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    private String sendMsg(String accessToken, Map<String, Object> message) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException{
		String reslut = HttpKit.post(MESSAGE_URL.concat(accessToken), JSONObject.toJSONString(message));
		return reslut;
	}
    
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
        Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("content", text);
        json.put("touser", openId);
        json.put("msgtype", "text");
        json.put("text", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
}
