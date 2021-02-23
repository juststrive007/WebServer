package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import sun.nio.cs.ISO_8859_2;

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
            HttpResponse response=new HttpResponse(socket);
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
                response.setEntity(file);
                /**
                 * 将用户请求的该资源以标准的HTTP响应格式发送回给客户端
                 * 1：发送状态行
                 * 2：发送响应头
                 * 3：发送响应正文
                 *
                 */
                //1.发送状态行
                //2.发送响应头
                //3.发送响应正文
            }else {
                System.out.println("文件不存在！");
                //获取404.html
                File notFound=new File("./webapps/root/404.html");
                if(!notFound.exists()){
                    System.out.println("404.html not found");
                    return;
                }

                response.setEntity(notFound);
                response.setStatusCode(404);
                response.setStatusReason("NOT Found");

                //1.发送状态行
                //2.发送响应头
                //3. 发送消息体
            }


            System.out.println("ClientHandler:处理请求完毕！");

            //3响应客户端
            System.out.println("ClientHandler:开始发送响应！");
            response.flush();
            System.out.println("ClientHandler:发送响应完毕！");

            System.out.println("ClientHandler:处理完成");


        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            /**当响应完成客户端后，与客户端断开连接，此种操作是HTTP1.0的
             *  通讯方式,后期如果改为可以在一次连接中进行多次请求与响应时
             *  就无需做这个操作了
             */
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
