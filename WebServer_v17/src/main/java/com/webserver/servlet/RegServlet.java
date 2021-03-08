package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * 用于处理用户注册业务的类
 * @author wm
 */
public class RegServlet extends HttpServlet{
    public void service (HttpRequest request, HttpResponse response){
        /**
         * 1.通过request获取用户提交的注册信息
         * 2.将该用户写入user.dat文件保存
         * 3.设置response相应注册结果页面
         */
        System.out.println("RegServlet:开始处理注册业务。。。");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String nickname=request.getParameter("nickname");
        String ageline=request.getParameter("age");
        int age=0;
        if ("".equals(ageline)){

             age = Integer.parseInt(ageline);
        }
        System.out.println(
                username +","+password+","+ nickname+","+ age
        );
        /**
         * 2
         * 将该用户信息写入user.dat文件，一个用户占用100字节，其中用户名，密码，
         * 昵称为字符串各占32字节，年龄为int值占4个字节。
         */

        try(
                RandomAccessFile raf=new RandomAccessFile("user.dat","rw");
        ){
            //判断用户是否存在
            /**
             * 首先应当循环读取user.dat文件中的现有数据，
             * 将每条记录的用户名读取出来并进行比对，如果是重复用户，则直接设置
             * response响应给用户have_user.html页面，告知其该用户已存在。
             * 如果user.dat文件现有数据没有该用户则执行注册操作。
             *
             */
//
//            boolean havaUser=false;
//            long point=0l;
//            while (  (point=raf.getFilePointer()) <= raf.length()) {
//                byte[] rdata=new byte[32];
//                try {
//                    raf.read(rdata);
//                    String readname=new String(rdata).trim();
//                    System.out.println(readname);
//                    if (username.equals(readname)) {
//                        havaUser=true;
//                    }
//                    point=point+100l;
//                    raf.seek(point);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if(havaUser){
//                File  file = new File("./webapps/myweb/hava_user.html");
//
//                response.setEntity(file);
//
//            }else {

            for(int i=0;i<raf.length()/100;i++){
                //移动到每条记录的开始位置
                raf.seek(i*100);
                byte[] data=new byte[32];
                raf.read(data);
                String name=new String(data,"UTF-8").trim();
                if(username.equals(name)){
                    forward("/myweb/hava_user.html",request,response);

                    return;
                }
            }

            //先将指针移动到文件末尾
            raf.seek(raf.length());
            //写用户名
            byte[] data = username.getBytes("UTF-8");
            //将数组扩容至32字节
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //写密码
            data = password.getBytes("UTF-8");
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //写昵称
            data = nickname.getBytes("UTF-8");
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //写age writeint
            raf.writeInt(age);
            System.out.println("注册完毕");

            //3.返回页面设置
            forward("/myweb/reg_success.html",request,response);


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("RegServlet:处理注册业务员完毕！");

    }
}
