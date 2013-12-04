package com.gson.oauth;

import java.util.Map;

import com.gson.util.Common;
import com.gson.util.HttpKit;

/**
 * 菜单,可以将accessToken
 * 存储在session或者memcache中
 * @author L.cm
 * @date 2013-11-5 下午3:17:33
 */
public class Menu {

    /**
     * 创建菜单
     */
    public static boolean createMenu(String params) {
        String accessToken = WeChat.getAccessToken();
        String jsonStr = HttpKit.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken, params);
        Map<String, Object> map = Common.jsonToMap(jsonStr);
        return "0".equals(map.get("errcode").toString());
    }
    
    /**
     * 查询菜单
     */
    public static Map<String, Object> getMenuInfo() {
        String accessToken = WeChat.getAccessToken();
        String jsonStr = HttpKit.get("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken);
        Map<String, Object> map = Common.jsonToMap(jsonStr);
        return map;
    }
    
    /**
     * 删除自定义菜单
     */
    public static boolean deleteMenu() {
        String accessToken = WeChat.getAccessToken();
        String jsonStr = HttpKit.get("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);
        Map<String, Object> map = Common.jsonToMap(jsonStr);
        return "0".equals(map.get("errcode").toString());
    }
    
    public static void main(String[] args) {
        System.out.println(getMenuInfo());
    }
}
