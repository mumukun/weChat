/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gson.bean.Articles;
import com.gson.bean.InMessage;
import com.gson.bean.OutMessage;
import com.gson.bean.TextOutMessage;
import com.gson.inf.MessageProcessingHandler;
import com.gson.util.ConfKit;
import com.gson.util.HttpKit;
import com.gson.util.Tools;
import com.gson.util.XStreamFactory;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;

/**
 * 微信常用的API
 *
 * @author L.cm & ____′↘夏悸
 * @date 2013-11-5 下午3:01:20
 */
public class WeChat {
    private static final Logger LOGGER = Logger.getLogger(WeChat.class);
    private static final String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
    private static final String PAYFEEDBACK_URL = "https://api.weixin.qq.com/payfeedback/update";
    private static final String DEFAULT_HANDLER = "com.gson.inf.DefaultMessageProcessingHandlerImpl";
    private static MessageProcessingHandler messageProcessingHandler = null;

    /**
     * 获取access_token
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     */
    public static String getAccessToken() {
        String appid = ConfKit.get("AppId");
        String secret = ConfKit.get("AppSecret");
        String jsonStr = HttpKit.get(ACCESSTOKEN_URL.concat("&appid=") + appid + "&secret=" + secret);
        Map<String, Object> map = JSONObject.parseObject(jsonStr);
        return map.get("access_token").toString();
    }

    /**
     * 支付反馈
     * @param openid
     * @param feedbackid
     * @return
     */
    public static boolean payfeedback(String openid, String feedbackid) {
        Map<String, String> map = new HashMap<String, String>();
        String accessToken = getAccessToken();
        map.put("access_token", accessToken);
        map.put("openid", openid);
        map.put("feedbackid", feedbackid);
        String jsonStr = HttpKit.get(PAYFEEDBACK_URL, map);
        Map<String, Object> jsonMap = JSONObject.parseObject(jsonStr);
        return "0".equals(jsonMap.get("errcode").toString());
    }

    /**
     * 签名检查
     * @param token
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static Boolean checkSignature(String token, String signature, String timestamp, String nonce) {
        return Tools.checkSignature(token, signature, timestamp, nonce);
    }

    /**
     * 根据接收到用户消息进行处理
     * @param responseInputString   微信发送过来的xml消息体
     * @return
     */
    public static String processing(String responseInputString) {
        InMessage inMessage = parsingInMessage(responseInputString);
        OutMessage oms = new OutMessage();
        // 加载处理器
        if (messageProcessingHandler == null) {
            // 获取自定消息处理器，如果自定义处理器则使用默认处理器。
            String handler = ConfKit.get("MessageProcessingHandlerImpl");
            handler = handler == null ? DEFAULT_HANDLER : handler;
            try {
                Class<?> clazz = Class.forName(handler);
                messageProcessingHandler = (MessageProcessingHandler) clazz.newInstance();
            } catch (Exception e) {
                LOGGER.error("messageProcessingHandler Load Error！", e);
                throw new RuntimeException("messageProcessingHandler Load Error！");
            }
        }

        try {
            //取得消息类型
            String type = inMessage.getMsgType();
            Method mt = messageProcessingHandler.getClass().getMethod(type + "TypeMsg", InMessage.class);
            oms = (OutMessage) mt.invoke(messageProcessingHandler, inMessage);
            if (oms == null) {
                oms = new TextOutMessage();
                ((TextOutMessage) oms).setContent("系统错误!");
            }
            setMsgInfo(oms, inMessage);
        } catch (Exception e) {
            LOGGER.error(e);
            oms = new TextOutMessage();
            ((TextOutMessage) oms).setContent("系统错误!");
            try {
                setMsgInfo(oms, inMessage);
            } catch (Exception e1) {
                LOGGER.error(e);
            }
        }

        // 把发送发送对象转换为xml输出
        XStream xs = XStreamFactory.init(true);
        xs.alias("xml", oms.getClass());
        xs.alias("item", Articles.class);
        String xml = xs.toXML(oms);

        LOGGER.debug("输出消息:[" + xml + "]");

        return xml;
    }

    /**
     * 获取用户信息
     * @param openid
     * @return
     */
    public static Map<String, Object> getInfo(String openid) {
        String accessToken = WeChat.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid" + openid;
        String jsonStr = HttpKit.get(url);
        return JSON.parseObject(jsonStr,Map.class);
    }

    /**
     * 设置发送消息体
     * @param oms
     * @param msg
     * @throws Exception
     */
    private static void setMsgInfo(OutMessage oms,InMessage msg) throws Exception {
        Class<?> outMsg = oms.getClass().getSuperclass();
        Field CreateTime = outMsg.getDeclaredField("CreateTime");
        Field ToUserName = outMsg.getDeclaredField("ToUserName");
        Field FromUserName = outMsg.getDeclaredField("FromUserName");

        ToUserName.setAccessible(true);
        CreateTime.setAccessible(true);
        FromUserName.setAccessible(true);

        CreateTime.set(oms, new Date().getTime());
        ToUserName.set(oms, msg.getFromUserName());
        FromUserName.set(oms, msg.getToUserName());
    }

    /**
     *消息体转换
     * @param responseInputString
     * @return
     */
    private static InMessage parsingInMessage(String responseInputString) {
        //转换微信post过来的xml内容
        XStream xs = XStreamFactory.init(false);
        xs.alias("xml", InMessage.class);
        InMessage msg = (InMessage) xs.fromXML(responseInputString);
        return msg;
    }
}
