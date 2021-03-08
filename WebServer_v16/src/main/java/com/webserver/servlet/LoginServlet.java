package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LoginServlet {
    public void service(HttpRequest request, HttpResponse response){
        String name=request.getParameter("username");
        String password=request.getParameter("password");

        File file=  new File("./user.dat");
        try {
            RandomAccessFile raf=new RandomAccessFile(file,"rw");
            byte[] data=new byte[32];
            for(int i=0;i<raf.length()/100;i++){
                raf.seek(i*100);
                raf.read(data);
                //注意读取的字节是32字节，然后需要去除空白字符串
                String readName=new String(data).trim();
                raf.read(data);
                String readPassword = new String(data).trim();
                //比名称与密码
                if(readName.equals(name)&&readPassword.equals(password)){
                    //登录成功
                    response.setEntity(new File(
                            "./webapps/myweb/login_success.html"));
                    return;
                }
            }
            //登录失败
            response.setEntity(new File("./webapps/myweb/login_fail.html"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
