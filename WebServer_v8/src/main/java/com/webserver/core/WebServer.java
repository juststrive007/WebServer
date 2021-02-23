package com.webserver.core;

import com.sun.corba.se.spi.activation.Server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * WebServer 是模拟Tomcat的一个简单的web容器
 * web容器的职责有很多，主要有：
 * 1：与客户端（通常是浏览器）维护TCP的连接以及基于HTpp
 *  协议的应用交互
 * 2：管理多个webAPP，每个webAPP是一个网路应用
 * 它应当包含：网页、静态资源、业务代码（javaa代码）
 * 等内容
 *
 * @author wm
 */
public class WebServer {

    private ServerSocket server;

    public WebServer(){
        try {
            System.out.println("正在启动服务端。。");
            server=new ServerSocket(8088);
            System.out.println("服务端启动完毕！");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void start(){
        try {
            //暂不开启接收多次客户端请求连接
            while(true) {
                System.out.println("等待客户端连接");
                Socket socket = server.accept();
                System.out.println("一个客户端连接了");

                //启动一个线程处理该客户端的交互
                ClientHandler handler = new ClientHandler(socket);
                Thread t = new Thread(handler);
                t.start();
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        WebServer server=new WebServer();
        server.start();

    }





}
