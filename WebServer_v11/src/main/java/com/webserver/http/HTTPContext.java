package com.webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 该类定义所有HTTP协议规定内容
 * @author wm
 */
public class HTTPContext {
    /**
     * 回车符
     */
    public static final char CR = 13;
    /**
     * 换行符
     */
    public static final char LF = 10;

    /**
     * 资源后缀与Content-Type的对应关系
     * key:资源的后缀名
     * value：该资源对应的Content-Type的值
     */
    private static Map<String,String> mimeMapping = new HashMap<>();

    static {
        //初始化静态资源
        initMimeMapping();
    }

    /**
     * 初始化mimeMapping
     */
    private static void initMimeMapping(){
        mimeMapping.put("html","text/html");
        mimeMapping.put("css","text/css");
        mimeMapping.put("js","application/javascript");
        mimeMapping.put("png","image/png");
        mimeMapping.put("gif","image/gif");
        mimeMapping.put("jpg","image/jpeg");

    }

    public static String getContenType(String key){
        return mimeMapping.get(key);
    }
}
