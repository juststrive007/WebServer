package com.webserver.http;

import java.io.InputStream;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送过来的一个请求内容
 * 每个请求内容由三部分构成：
 * 1：请求行
 * 2：消息头
 * 3：消息正文
 */
public class HttpRequest {
    //请求行相关信息

    //消息头相关信息

    //消息正文相关信息


    /**
     * HttpRequest的构造方法，实例化的过程就是读取
     * 客户端发送过来的请求病解析的过程。实例化后的当前
     * 对象即表示此次发送过来的请求内容。
     * @param in
     */
    public HttpRequest(InputStream in){



    }
}
