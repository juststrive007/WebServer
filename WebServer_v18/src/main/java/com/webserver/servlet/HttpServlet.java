package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;

public  abstract class HttpServlet {
    public abstract void service(
            HttpRequest request,
            HttpResponse response
    );

    /**
     * 调转到指定界面
     * @param path
     * @param request
     * @param response
     */
    public void forward(String path,HttpRequest request,HttpResponse response){
        File file=new File("./webapps"+path);
        response.setEntity(file);
    }
}
