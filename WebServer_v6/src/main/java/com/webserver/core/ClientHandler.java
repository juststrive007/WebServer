package com.webserver.core;

import com.webserver.http.HttpRequest;
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
                /**
                 * ���û�����ĸ���Դ�Ա�׼��HTTP��Ӧ��ʽ���ͻظ��ͻ���
                 * 1������״̬��
                 * 2��������Ӧͷ
                 * 3��������Ӧ����
                 *
                 */
                //ͨ��socket��ȡ����������ڸ��ͻ��˷�����Ϣ
                OutputStream out= socket.getOutputStream();

                //1.����״̬��
                String line="HTTP/1.0 200 ok";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);//wirtten CR
                out.write(10);//wirtten LF

                //2.������Ӧͷ
                /**
                 *��Ӧ�г�����������Ӧͷ��
                 * Content-Type�����ڸ�֪�������Ӧ������������ʲô����
                 * �����磺ҳ�棬ͼƬ�ȣ����������Ӧͷ�в�ָ�������������������
                 * �жϣ���ô���������֮ǰ�������ַ���ж�
                 *
                 * Content-Length,���ڸ�֪�������Ӧ���ĵ�ʵ�ʳ��ȣ���λ��
                 * �ֽڡ���������ڶ�ȡ��Ӧ����ʵ�ʶ�ȡ���ֽ����������������ݡ�
                 */
                line="Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                line="Content-Length: "+file.length();
                out.write(13);
                out.write(10);
                //��������CRLF����ʾ��Ӧͷ�������
                out.write(13);
                out.write(10);

                //3.������Ӧ����
                FileInputStream fis= new FileInputStream(file);
                int len=-1;
                byte[] data= new byte[1024*10];
                while ( (len=fis.read(data))!= -1){
                    out.write(data,0,len);
                }

            }else {
                System.out.println("�ļ������ڣ�");
                //��ȡ404.html
                File file1=new File("./webapps/root/404.html");
                if(!file1.exists()){
                    System.out.println("404.html not found");
                    return;
                }
                //��ȡsocket�������������Ϣ
                OutputStream out =  socket.getOutputStream();

                //1.����״̬��
                String line="HTTP/1.1 404 Not Found";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                //2.������Ӧͷ
                // Content-Type  ��Content-Length
                line="Content-Type: text/html";
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                line="Content-Length: "+file1.length();
                out.write(line.getBytes("ISO8859-1"));
                out.write(13);
                out.write(10);

                //������Ϣͷ
                out.write(13);
                out.write(10);

                //3. ������Ϣ��
                System.out.println("������Ϣ��");
                FileInputStream fis1=new FileInputStream(file1);
                int len1=-1;
                byte[] data=new byte[1024*10];
                while ((len1=fis1.read(data))!= -1 ){
                    out.write(data,0,len1);
                }
                /**
                 File notFoundPage
                 = new File("./webapps/root/404.html");

                 OutputStream out = socket.getOutputStream();
                 String line = "HTTP/1.1 404 Not Found";
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 line = "Content-Type: text/html";
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 line = "Content-Length: "+notFoundPage.length();
                 out.write(line.getBytes("ISO8859-1"));
                 out.write(13);
                 out.write(10);

                 out.write(13);
                 out.write(10);

                 FileInputStream fis
                 = new FileInputStream(notFoundPage);
                 int len = -1;
                 byte[] data = new byte[1024*10];
                 while((len = fis.read(data))!=-1) {
                 out.write(data,0,len);
                 }
                 */


            }


            System.out.println("ClientHandler:����������ϣ�");

            //3��Ӧ�ͻ���

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
