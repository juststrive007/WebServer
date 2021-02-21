package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.*;
import java.net.Socket;

/**
 * 客户端处理器，该类负责与指定的客户端交互
 * 对于HTTP协议而言，客户端与服务端的交互由三部分组成
 * 1：解析请求
 * 2：处理请求
 * 3：响应客户端
 * @author wm
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    public void run(){
        try{
           System.out.println("ClientHandler:开始处理");
            //1解析请求
            System.out.println("ClientHandler：开始解析请求");
            HttpRequest request=new HttpRequest(socket);
            System.out.println("ClientHandler:解析请求完毕！");

            //2处理请求
            //2.1根据请求请求对象获取用户请求的资源的路径
            System.out.println("ClientHandler:开始处理请求");
            String Path=request.getUri();
            System.out.println("Path:"+Path);
            //2.2根据用户请求的资源路径去webapps目录下寻找webapp的文件
            File file= new File("./webapps"+Path);
            if(file.exists()){
                System.out.println("文件已找到！");
                /**
                 * 将用户请求的该资源以标准的HTTP响应格式发送回给客户端
                 * 1：发送状态行
                 * 2：发送响应头
                 * 3：发送响应正文
                 *
                 */
                //通过socket获取输出流，用于给客户端发送消息
                OutputStream out= socket.getOutputStream();

                //1.发送状态行
                String line="HTTP/1.1 200 ok";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//wirtten CR
                out.write(10);//wirtten LF

                //2.发送响应头

                //3.发送响应正文


            }else {
                System.out.println("文件不存在！");
            }


            System.out.println("ClientHandler:处理请求完毕！");

            //3响应客户端

            System.out.println("ClientHandler:处理完成");


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
