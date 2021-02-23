package com.webserver.core;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import sun.nio.cs.ISO_8859_2;

import java.io.*;
import java.net.Socket;

/**
 * �ͻ��˴����������ฺ����ָ���Ŀͻ��˽���
 * ����HTTPЭ����ԣ��ͻ��������˵Ľ��������������
 * 1����������
 * 2����������
 * 3����Ӧ�ͻ���
 * @author wm
 */
public class ClientHandler implements Runnable{
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket=socket;
    }

    public void run(){
        try{
            System.out.println("ClientHandler:��ʼ����");
            //1��������
            System.out.println("ClientHandler����ʼ��������");
            HttpRequest request=new HttpRequest(socket);
            HttpResponse response=new HttpResponse(socket);
            System.out.println("ClientHandler:����������ϣ�");

            //2��������
            //2.1����������������ȡ�û��������Դ��·��
            System.out.println("ClientHandler:��ʼ��������");
            String Path=request.getUri();
            System.out.println("Path:"+Path);
            //2.2�����û��������Դ·��ȥwebappsĿ¼��Ѱ��webapp���ļ�
            File file= new File("./webapps"+Path);
            if(file.exists()){
                System.out.println("�ļ����ҵ���");
                response.setEntity(file);
                /**
                 * ���û�����ĸ���Դ�Ա�׼��HTTP��Ӧ��ʽ���ͻظ��ͻ���
                 * 1������״̬��
                 * 2��������Ӧͷ
                 * 3��������Ӧ����
                 *
                 */
                //1.����״̬��
                //2.������Ӧͷ
                //3.������Ӧ����
            }else {
                System.out.println("�ļ������ڣ�");
                //��ȡ404.html
                File notFound=new File("./webapps/root/404.html");
                if(!notFound.exists()){
                    System.out.println("404.html not found");
                    return;
                }

                response.setEntity(notFound);
                response.setStatusCode(404);
                response.setStatusReason("NOT Found");

                //1.����״̬��
                //2.������Ӧͷ
                //3. ������Ϣ��
            }


            System.out.println("ClientHandler:����������ϣ�");

            //3��Ӧ�ͻ���
            System.out.println("ClientHandler:��ʼ������Ӧ��");
            response.flush();
            System.out.println("ClientHandler:������Ӧ��ϣ�");

            System.out.println("ClientHandler:�������");


        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            /**����Ӧ��ɿͻ��˺���ͻ��˶Ͽ����ӣ����ֲ�����HTTP1.0��
             *  ͨѶ��ʽ,���������Ϊ������һ�������н��ж����������Ӧʱ
             *  �����������������
             */
            try{
                socket.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
