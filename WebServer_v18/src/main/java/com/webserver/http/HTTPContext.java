package com.webserver.http;



import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
//        mimeMapping.put("html","text/html");
//        mimeMapping.put("css","text/css");
//        mimeMapping.put("js","application/javascript");
//        mimeMapping.put("png","image/png");
//        mimeMapping.put("gif","image/gif");
//        mimeMapping.put("jpg","image/jpeg");
        /**
         * 使用dom4j解析conf/web.xml文件
         * 将根标签下所有名为mime-mapping的子标签获取回来
         * 并将每个mime-mapping的子标签：
         * extension中间的文本作为key
         * mime-type中间的文本作为value
         * 存入mimeMapping这个Map中完成初始化
         */
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File("./conf/web.xml"));
            Element root=doc.getRootElement();
            List<Element> mimemapinglist=root.elements("mime-mapping");
            for(Element mimemaping:mimemapinglist){
                String extension=mimemaping.elementText("extension");
                String mime_type=mimemaping.elementText("mime-type");
                mimeMapping.put(extension,mime_type);
            }
            System.out.println(mimeMapping.size());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String getContenType(String key){
        return mimeMapping.get(key);
    }

    public static void main(String[] args) {
        String type=getContenType("js");
        System.out.println(type);
    }
}
