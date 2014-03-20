/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2014 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.oauth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.gson.bean.Articles;
import com.gson.util.HttpKit;

/**
 * 客服消息接口
 *
 * @author L.cm
 * @date 2013-11-5 下午3:32:30
 * @description 当用户主动发消息给公众号的时候（包括发送信息、点击自定义菜单、订阅事件、扫描二维码事件、支付成功事件、用户维权），微信将会把消息数据推送给开发者，开发者在一段时间内（目前修改为48小时）可以调用客服消息接口，通过POST一个JSON数据包来发送消息给普通用户，在48小时内不限制发送次数。此接口主要用于客服等有人工消息处理环节的功能，方便开发者为用户提供更加优质的服务。
 */
public class Message {

    private static final String MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    /**
     * 发送客服消息
     * @param accessToken
     * @param message
     * @return
     * @throws Exception
     */
    private String sendMsg(String accessToken, Map<String, Object> message) throws Exception{
		String reslut = HttpKit.post(MESSAGE_URL.concat(accessToken), JSONObject.toJSONString(message));
		return reslut;
	}
    
    /**
     * 发送文本客服消息
     * @param openId
     * @param text
     * @throws Exception 
     */
    public String sendText(String accessToken,String openId, String text) throws Exception {
        Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("content", text);
        json.put("touser", openId);
        json.put("msgtype", "text");
        json.put("text", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
    
    /**
     * 发送图片消息
     * @param accessToken
     * @param openId
     * @param media_id
     * @return
     * @throws Exception
     */
    public String SendImage(String accessToken,String openId, String media_id) throws Exception{
    	Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "image");
        json.put("image", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
    
    /**
     * 发送语言回复
     * @param accessToken
     * @param openId
     * @param media_id
     * @return
     * @throws Exception
     */
    public String SendVoice(String accessToken,String openId, String media_id) throws Exception{
    	Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("media_id", media_id);
        json.put("touser", openId);
        json.put("msgtype", "voice");
        json.put("voice", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
    
    /**
     * 发送视频回复
     * @param accessToken
     * @param openId
     * @param media_id
     * @param title
     * @param description
     * @return
     * @throws Exception
     */
    public String SendVideo(String accessToken,String openId, String media_id,String title,String description) throws Exception{
    	Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("media_id", media_id);
        textObj.put("title", title);
        textObj.put("description", description);
        
        json.put("touser", openId);
        json.put("msgtype", "video");
        json.put("video", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
    
    /**
     * 发送音乐回复
     * @param accessToken
     * @param openId
     * @param musicurl
     * @param hqmusicurl
     * @param thumb_media_id
     * @param title
     * @param description
     * @return
     * @throws Exception
     */
    public String SendMusic(String accessToken,String openId, String musicurl,String hqmusicurl,String thumb_media_id,String title,String description) throws Exception{
    	Map<String,Object> json = new HashMap<String,Object>();
        Map<String,Object> textObj = new HashMap<String,Object>();
        textObj.put("musicurl", musicurl);
        textObj.put("hqmusicurl", hqmusicurl);
        textObj.put("thumb_media_id", thumb_media_id);
        textObj.put("title", title);
        textObj.put("description", description);
        
        json.put("touser", openId);
        json.put("msgtype", "music");
        json.put("music", textObj);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
    
    /**
     * 发送图文回复
     * @param accessToken
     * @param openId
     * @param articles
     * @return
     * @throws Exception
     */
    public String SendNews(String accessToken,String openId, List<Articles> articles) throws Exception{
    	Map<String,Object> json = new HashMap<String,Object>();
        json.put("touser", openId);
        json.put("msgtype", "news");
        json.put("voice", articles);
    	String reslut = sendMsg(accessToken, json);
        return reslut;
    }
}
