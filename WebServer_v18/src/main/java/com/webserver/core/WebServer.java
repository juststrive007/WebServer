package com.webserver.core;

import com.sun.corba.se.spi.activation.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer ��ģ��Tomcat��һ���򵥵�web����
 * web������ְ���кܶ࣬��Ҫ�У�
 * 1����ͻ��ˣ�ͨ�����������ά��TCP�������Լ�����HTpp
 *  Э���Ӧ�ý���
 * 2��������webAPP��ÿ��webAPP��һ����·Ӧ��
 * ��Ӧ����������ҳ����̬��Դ��ҵ����루javaa���룩
 * ������
 *
 * @author wm
 */
public class WebServer {

    private ServerSocket server;

    private ExecutorService threadPoll;
    public WebServer(){
        try {
            System.out.println("������������ˡ���");
            server=new ServerSocket(8088);
            threadPoll= Executors.newFixedThreadPool(50);
            System.out.println("�����������ϣ�");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void start(){
        try {
            //�ݲ��������ն�οͻ�����������
            while(true) {
                System.out.println("�ȴ��ͻ�������");
                Socket socket = server.accept();
                System.out.println("һ���ͻ���������");

                //����һ���̴߳���ÿͻ��˵Ľ���
                ClientHandler handler = new ClientHandler(socket);
//                Thread t = new Thread(handler);
//                t.start();
                threadPoll.execute(handler);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        WebServer server=new WebServer();
        server.start();

    }





}
