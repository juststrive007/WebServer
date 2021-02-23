package com.webserver.core;

import com.webserver.http.HttpRequest;
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
                String line="HTTP/1.0 200 ok";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//wirtten CR
                out.write(10);//wirtten LF

                //2.发送响应头
                /**
                 *响应中常见的两个响应头：
                 * Content-Type，用于告知浏览器响应正文中内容是什么数据
                 * （比如：页面，图片等），如果在响应头中不指定，则是让浏览器自行
                 * 判断，那么浏览器会结合之前的请求地址来判断
                 *
                 * Content-Length,用于告知浏览器响应正文的实际长度，单位是
                 * 字节。浏览器则在读取响应正文实际读取该字节量来接收正文内容。
                 */
                line="Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                line="Content-Length: "+file.length();
                out.write(13);
                out.write(10);
                //单独发送CRLF，表示响应头发送完毕
                out.write(13);
                out.write(10);

                //3.发送响应正文
                FileInputStream fis= new FileInputStream(file);
                int len=-1;
                byte[] data= new byte[1024*10];
                while ( (len=fis.read(data))!= -1){
                    out.write(data,0,len);
                }

            }else {
                System.out.println("文件不存在！");
                //获取404.html
                File file1=new File("./webapps/root/404.html");
                if(!file1.exists()){
                    System.out.println("404.html not found");
                    return;
                }
                //获取socket输出流，发送信息
                OutputStream out =  socket.getOutputStream();

                //1.发送状态行
                String line="HTTP/1.1 404 Not Found";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                //2.发送响应头
                // Content-Type  与Content-Length
                line="Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                line="Content-Length: "+file1.length();
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                //结束消息头
                out.write(13);
                out.write(10);

                //3. 发送消息体
                System.out.println("发送消息体");
                FileInputStream fis1=new FileInputStream(file1);
                int len1=-1;
                byte[] data=new byte[1024*10];
                while ((len1=fis1.read(data))!= -1 ){
                    out.write(data,0,len1);
                }
                /**
                 File notFoundPage
                 = new File("./webapps/root/404.html");

                 OutputStream out = socket.getOutputStream();
                 String line = "HTTP/1.1 404 Not Found";
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 line = "Content-Type: text/html";
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 line = "Content-Length: "+notFoundPage.length();
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 out.write(13);
                 out.write(10);

                 FileInputStream fis
                 = new FileInputStream(notFoundPage);
                 int len = -1;
                 byte[] data = new byte[1024*10];
                 while((len = fis.read(data))!=-1) {
                 out.write(data,0,len);
                 }
                 */


            }


            System.out.println("ClientHandler:处理请求完毕！");

            //3响应客户端

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
