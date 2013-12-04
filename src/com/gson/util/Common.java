package com.gson.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 常用的一些方法
 * @author L.cm
 * @date 2013-11-5 下午3:06:38
 */
public class Common {

    // Json 转map
    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonToMap(String jsonStr) {
        return JSON.parseObject(jsonStr, Map.class);
    }
    
    
    
}
