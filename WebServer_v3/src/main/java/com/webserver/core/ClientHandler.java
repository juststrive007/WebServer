package com.webserver.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            InputStream in=socket.getInputStream();

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
            System.out.print(line);


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
