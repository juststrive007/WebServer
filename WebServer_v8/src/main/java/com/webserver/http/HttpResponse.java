package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 响应对象
 * 该类的每个实例用于表示服务端给客户端发送的响应
 * 内容
 * 一个响应由三部分构成；
 * 状态行
 * 响应头
 * 响应正文
 * @author wm
 */
public class HttpResponse {
    //跟连接相关的信息

    private Socket socket;
    private OutputStream out;
    //

    //状态行相关信息
    //状态码，默认值200.因为大多数响应都是成功的。
    private int statusCode=200;
    private String statusReason="OK";


    //响应头相关信息

    //响应正文相关信息

    /**
     * 响应正文的实体文件，该文件通常是用户请求的实际资源文件（页面，
     * 图片等内容），这里会将该文件的内容作为响应正文发送给客户端

     */
    private File entity;

    //构造方法
    public HttpResponse(Socket socket){
        this.socket=socket;
        try{
            out=socket.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 将当前响应对象的内容以标准的HTTP响应
     * 格式发送给客户端。
     */
    public void flush(){
        try{
            /**
             * 发送一个响应分为三步
             * 1.发送状态行
             * 2.发送响应头
             * 3.发送响应正文
             *
             */
            System.out.println("HttpResponse:开始发送响应。。");
            sendStatusLine();
            sendHeaders();
            sendContent();
            System.out.println("HttpResponse:发送响应完毕。。");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //发送状态行
    private void sendStatusLine(){
        System.out.println("HttpResponse:开始发送状态行");
        try{
            String line="HTTP/1.1"+" "+statusCode+" "+statusReason;
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);//wirtten CR
            out.write(10);//wirtten LF

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //发送响应头
    private  void sendHeaders(){
        System.out.println("HttpResponse:开始发送响应头");
        try{
            /**
             *响应中常见的两个响应头：
             * Content-Type，用于告知浏览器响应正文中内容是什么数据
             * （比如：页面，图片等），如果在响应头中不指定，则是让浏览器自行
             * 判断，那么浏览器会结合之前的请求地址来判断
             *
             * Content-Length,用于告知浏览器响应正文的实际长度，单位是
             * 字节。浏览器则在读取响应正文实际读取该字节量来接收正文内容。
             */
            String line="Content-Type: text/html";
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);
            out.write(10);

            line="Content-Length: "+entity.length();
            out.write(13);
            out.write(10);
            //单独发送CRLF，表示响应头发送完毕
            out.write(13);
            out.write(10);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //发送响应正文
    private void sendContent(){
        System.out.println("HttpResponse:开始发送响应正文");
        //为了保证文件流自动关闭，将文件流打开放在try中
        try(
                FileInputStream fis
                        = new FileInputStream(entity);
        ){
            int len=-1;
            byte[] data= new byte[1024*10];
            while ( (len=fis.read(data))!= -1){
                out.write(data,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
}
