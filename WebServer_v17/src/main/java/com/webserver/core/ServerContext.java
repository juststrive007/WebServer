package com.webserver.core;



import com.webserver.servlet.HttpServlet;
import com.webserver.servlet.LoginServlet;
import com.webserver.servlet.RegServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务端相关配置信息都放在这里
 * @author wm
 */
public class ServerContext {
    /**
     * 请求与对应得Servlet的关系
     * key:请求路径
     * value:处理该请求的Servlet
     *
     */
    private static Map<String, HttpServlet> servletMapping=new HashMap<>();

    static{
        try {
            initServletMapping();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void initServletMapping() throws ClassNotFoundException, IllegalAccessException, InstantiationException, DocumentException {
        /**
         * 解析conf/servlets.xml文件
         * 将根标签下所有<servelet>标签获取到
         * 并将其属性path的值作为key
         * 将属性className的值得到后利用反射
         * 加载该类并实例化，将实例化的对象作为
         * value存入到servletMapping完成初始化。
         */
        SAXReader  reader=new SAXReader();
        Document doc=reader.read(new File("./conf/servlets.xml"));
        Element root=doc.getRootElement();

        List<Element> servlets=root.elements("servelet");
        for(Element e:servlets){
            String path=e.attributeValue("path");
            String className=e.attributeValue("className");

            Class cls=Class.forName(className);
            Object obj=cls.newInstance();

            servletMapping.put(path, (HttpServlet)obj);

        }
        System.out.println(servletMapping);


//        servletMapping.put("/myweb/reg",new RegServlet());
//        servletMapping.put("/myweb/login",new LoginServlet());
    }

    /**
     * 根据给定的请求路径获取对应得Servlet，若该请求没有对应得Servlet则返回null
     * @param path
     * @return
     */
    public static HttpServlet getServlet(String path ) {

        return servletMapping.get(path);
    }
}
