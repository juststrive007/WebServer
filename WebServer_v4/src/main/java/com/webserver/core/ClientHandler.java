package com.webserver.core;

import com.webserver.http.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            }else {
                System.out.println("�ļ������ڣ�");
            }


            System.out.println("ClientHandler:����������ϣ�");

            //3��Ӧ�ͻ���

            System.out.println("ClientHandler:�������");


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}