package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送过来的一个请求内容
 * 每个请求内容由三部分构成：
 * 1：请求行
 * 2：消息头
 * 3：消息正文
 */
public class HttpRequest {
    //和连接相关的属性
    private Socket socket;
    private InputStream in;


    //请求行相关信息
    //请求方式
    private  String  method;
    //请求资源的抽象路径
    private  String  uri;
    //请求协议
    private  String  protocol;

    //消息头相关信息
    private Map<String,String> headers =new HashMap();


    //消息正文相关信息


    /**
     * HttpRequest的构造方法，实例化的过程就是读取
     * 客户端发送过来的请求病解析的过程。实例化后的当前
     * 对象即表示此次发送过来的请求内容。
     * @param socket
     */
    public HttpRequest(Socket socket) {

        try{
            /**
             * 通过Socket获取输入流，以便下面三个方法解析客户端发送的
             * 请求时使用
             * 同时保存socket，以备他用。
             */
            this.socket=socket;
            in=this.socket.getInputStream();

            /**
             * 解析请求分为三步：
             * 1.解析请求行
             * 2.解析消息头
             * 3.解析消息正文
             */
            parseRequestLine();
            parseHeaders();
            parseContent();


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 解析请求行
     */
    private void parseRequestLine(){
        System.out.println("HttpRequest:开始解析请求行...");
        try {

            //读取客户端发送过来的第一行字符串
            //因为一个请求的第一行就是请求行内容
            String line=readLine();
            System.out.println("请求行的内容："+line);

            /**
             * 将请求行的内容按照空格拆分为三项
             * 并分别赋值给method，uri，protocol三个属性。
             *  split 和substring都可以
             */


            /**
             *
            int num= line.indexOf(' ');
            method=line.substring(0,num);
            int num1=line.lastIndexOf(' ');
            uri=line.substring(num+1,num1);
            protocol= line.substring(num1+1,line.length());
            System.out.println("method:" +method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);
             */

            String regex=" ";

            String[] s=line.split(regex);

            method=s[0];
            uri=s[1];
            protocol=s[2];
            System.out.println("method:" +method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);



        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("HttpRequest:解析请求行完毕");
    }

    /**
     * 解析消息头
     */
    private void parseHeaders(){
        System.out.println("HttpRequest:开始解析消息头...");
        try{

            while(true) {
                String line=readLine();
                //读取到消息头的末尾，单独一个空行
                if("".equals(line)){
//                if(line.length()==0){
                    break;
                }
                System.out.println("消息头："+ line);
                /**
                 * 将消息头每一行按磨耗空格拆分为两项
                 * 将第一项作为key，第二项作为value保存
                 * 到headers这个属性完成消息头的解析
                 */
               String[] keyvalue= line.split(": ");
                headers.put(keyvalue[0],keyvalue[1]);

            }
            System.out.println("headers:"+headers);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("HttpRequest:解析消息头完毕");
    }

    /**
     * 解析消息正文
     *
     */
    private void parseContent(){
        System.out.println("HttpRequest:开始解析消息正文...");

        System.out.println("HttpRequest:解析消息正文完毕");
    }

    /**
     * 通过输入流in读取客户端发送过来的一行字符串（以CRLF结尾），
     * 但是不含有CRLF。
     * 此方法在解析请求行和消息头时使用
     * @return
     */
    private String readLine() throws IOException {
        int d= -1;
        char c1='a';
        char c2='a';
        StringBuilder builder=new StringBuilder();
        while ((d=in.read())!=-1){
            c2=(char) d;
            //如果上次读取了CR，本次读取了LF
            if(c1==13&&c2==10){
                //停止读取工作
                break;
            }
            c1=c2; //本次读取的字符记录为上次读取
            builder.append(c2);
        }
        String line=builder.toString().trim();
        return line;
    }

    /**
     * 提供获取请求相关信息的一系列GET方法，这里不提供SET方法，因为当前
     * 请求对象表示的内容是客户端发送过来的内容，服务端不需要修改。
     * @return
     */
    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * 获取指定的消息头
     * @param name
     * @return
     */
    public String getHeader(String name){
        return headers.get(name);
    }
}
