package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ��Ӧ����
 * �����ÿ��ʵ�����ڱ�ʾ����˸��ͻ��˷��͵���Ӧ
 * ����
 * һ����Ӧ�������ֹ��ɣ�
 * ״̬��
 * ��Ӧͷ
 * ��Ӧ����
 * @author wm
 */
public class HttpResponse {
    //��������ص���Ϣ

    private Socket socket;
    private OutputStream out;
    //

    //״̬�������Ϣ
    //״̬�룬Ĭ��ֵ200.��Ϊ�������Ӧ���ǳɹ��ġ�
    private int statusCode=200;
    private String statusReason="OK";


    //��Ӧͷ�����Ϣ
    //�������Ҫ���͵���Ӧͷ��keyΪ��Ӧͷ���֣�valueΪ��Ӧ��ֵ��
    private Map<String,String> headers = new HashMap<>();

    //��Ӧ���������Ϣ

    /**
     * ��Ӧ���ĵ�ʵ���ļ������ļ�ͨ�����û������ʵ����Դ�ļ���ҳ�棬
     * ͼƬ�����ݣ�������Ὣ���ļ���������Ϊ��Ӧ���ķ��͸��ͻ���

     */
    private File entity;

    //���췽��
    public HttpResponse(Socket socket){
        this.socket=socket;
        try{
            out=socket.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * ����ǰ��Ӧ����������Ա�׼��HTTP��Ӧ
     * ��ʽ���͸��ͻ��ˡ�
     */
    public void flush(){
        try{
            /**
             * ����һ����Ӧ��Ϊ����
             * 1.����״̬��
             * 2.������Ӧͷ
             * 3.������Ӧ����
             *
             */
            System.out.println("HttpResponse:��ʼ������Ӧ����");
            sendStatusLine();
            sendHeaders();
            sendContent();
            System.out.println("HttpResponse:������Ӧ��ϡ���");


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //����״̬��
    private void sendStatusLine(){
        System.out.println("HttpResponse:��ʼ����״̬��");
        try{
            String line="HTTP/1.1"+" "+statusCode+" "+statusReason;
            out.write(line.getBytes("ISO8859-1"));
            out.write(13);//wirtten CR
            out.write(10);//wirtten LF

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //������Ӧͷ
    private  void sendHeaders(){
        System.out.println("HttpResponse:��ʼ������Ӧͷ");
        try{
            //��������header��������header����
            Set<Map.Entry<String,String>> set=headers.entrySet();
           for(Map.Entry<String,String> e :set){
               String key=e.getKey();
               String value=e.getValue();
               String line=key+": "+value;
               System.out.println(line);
               out.write(line.getBytes("ISO8859-1"));
               out.write(13);
               out.write(10);
           }
            //��������CRLF����ʾ��Ӧͷ�������
            out.write(13);
            out.write(10);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //������Ӧ����
    private void sendContent(){
        System.out.println("HttpResponse:��ʼ������Ӧ����");
        //Ϊ�˱�֤�ļ����Զ��رգ����ļ����򿪷���try��
        try(
                FileInputStream fis
                        = new FileInputStream(entity);
        ){
            int len=-1;
            byte[] data= new byte[1024*10];
            while ( (len=fis.read(data))!= -1){
                out.write(data,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public File getEntity() {
        return entity;
    }

    public void setEntity(File entity) {
        this.entity = entity;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * ���Ҫ���͵���Ӧͷ
     * @param name
     * @param value
     */
    public void putHeader(String name ,String value){
        headers.put(name,value);
    }


}
