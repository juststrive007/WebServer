package com.webserver.servlet;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * ���ڴ����û�ע��ҵ�����
 * @author wm
 */
public class RegServlet extends HttpServlet{
    public void service (HttpRequest request, HttpResponse response){
        /**
         * 1.ͨ��request��ȡ�û��ύ��ע����Ϣ
         * 2.�����û�д��user.dat�ļ�����
         * 3.����response��Ӧע����ҳ��
         */
        System.out.println("RegServlet:��ʼ����ע��ҵ�񡣡���");
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
         * �����û���Ϣд��user.dat�ļ���һ���û�ռ��100�ֽڣ������û��������룬
         * �ǳ�Ϊ�ַ�����ռ32�ֽڣ�����Ϊintֵռ4���ֽڡ�
         */

        try(
                RandomAccessFile raf=new RandomAccessFile("user.dat","rw");
        ){
            //�ж��û��Ƿ����
            /**
             * ����Ӧ��ѭ����ȡuser.dat�ļ��е��������ݣ�
             * ��ÿ����¼���û�����ȡ���������бȶԣ�������ظ��û�����ֱ������
             * response��Ӧ���û�have_user.htmlҳ�棬��֪����û��Ѵ��ڡ�
             * ���user.dat�ļ���������û�и��û���ִ��ע�������
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
                //�ƶ���ÿ����¼�Ŀ�ʼλ��
                raf.seek(i*100);
                byte[] data=new byte[32];
                raf.read(data);
                String name=new String(data,"UTF-8").trim();
                if(username.equals(name)){
                    forward("/myweb/hava_user.html",request,response);

                    return;
                }
            }

            //�Ƚ�ָ���ƶ����ļ�ĩβ
            raf.seek(raf.length());
            //д�û���
            byte[] data = username.getBytes("UTF-8");
            //������������32�ֽ�
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //д����
            data = password.getBytes("UTF-8");
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //д�ǳ�
            data = nickname.getBytes("UTF-8");
            data = Arrays.copyOf(data, 32);
            raf.write(data);
            //дage writeint
            raf.writeInt(age);
            System.out.println("ע�����");

            //3.����ҳ������
            forward("/myweb/reg_success.html",request,response);


        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("RegServlet:����ע��ҵ��Ա��ϣ�");

    }
}
