package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * �������
 * �����ÿһ��ʵ�����ڱ�ʾ�ͻ��˷��͹�����һ����������
 * ÿ�����������������ֹ��ɣ�
 * 1��������
 * 2����Ϣͷ
 * 3����Ϣ����
 */
public class HttpRequest {
    //��������ص�����
    private Socket socket;
    private InputStream in;


    //�����������Ϣ
    //����ʽ
    private  String  method;
    //������Դ�ĳ���·��
    private  String  uri;
    //����Э��
    private  String  protocol;

    //��Ϣͷ�����Ϣ
    private Map<String,String> headers =new HashMap();


    //��Ϣ���������Ϣ


    /**
     * HttpRequest�Ĺ��췽����ʵ�����Ĺ��̾��Ƕ�ȡ
     * �ͻ��˷��͹��������󲡽����Ĺ��̡�ʵ������ĵ�ǰ
     * ���󼴱�ʾ�˴η��͹������������ݡ�
     * @param socket
     */
    public HttpRequest(Socket socket) {

        try{
            /**
             * ͨ��Socket��ȡ���������Ա������������������ͻ��˷��͵�
             * ����ʱʹ��
             * ͬʱ����socket���Ա����á�
             */
            this.socket=socket;
            in=this.socket.getInputStream();

            /**
             * ���������Ϊ������
             * 1.����������
             * 2.������Ϣͷ
             * 3.������Ϣ����
             */
            parseRequestLine();
            parseHeaders();
            parseContent();


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * ����������
     */
    private void parseRequestLine(){
        System.out.println("HttpRequest:��ʼ����������...");
        try {

            //��ȡ�ͻ��˷��͹����ĵ�һ���ַ���
            //��Ϊһ������ĵ�һ�о�������������
            String line=readLine();
            System.out.println("�����е����ݣ�"+line);

            /**
             * �������е����ݰ��տո���Ϊ����
             * ���ֱ�ֵ��method��uri��protocol�������ԡ�
             *  split ��substring������
             */


            /**
             *
            int num= line.indexOf(' ');
            method=line.substring(0,num);
            int num1=line.lastIndexOf(' ');
            uri=line.substring(num+1,num1);
            protocol= line.substring(num1+1,line.length());
            System.out.println("method:" +method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);
             */

            String regex=" ";

            String[] s=line.split(regex);

            method=s[0];
            uri=s[1];
            protocol=s[2];
            System.out.println("method:" +method);
            System.out.println("uri:"+uri);
            System.out.println("protocol:"+protocol);



        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("HttpRequest:�������������");
    }

    /**
     * ������Ϣͷ
     */
    private void parseHeaders(){
        System.out.println("HttpRequest:��ʼ������Ϣͷ...");
        try{

            while(true) {
                String line=readLine();
                //��ȡ����Ϣͷ��ĩβ������һ������
                if("".equals(line)){
//                if(line.length()==0){
                    break;
                }
                System.out.println("��Ϣͷ��"+ line);
                /**
                 * ����Ϣͷÿһ�а�ĥ�Ŀո���Ϊ����
                 * ����һ����Ϊkey���ڶ�����Ϊvalue����
                 * ��headers������������Ϣͷ�Ľ���
                 */
               String[] keyvalue= line.split(": ");
                headers.put(keyvalue[0],keyvalue[1]);

            }
            System.out.println("headers:"+headers);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("HttpRequest:������Ϣͷ���");
    }

    /**
     * ������Ϣ����
     *
     */
    private void parseContent(){
        System.out.println("HttpRequest:��ʼ������Ϣ����...");

        System.out.println("HttpRequest:������Ϣ�������");
    }

    /**
     * ͨ��������in��ȡ�ͻ��˷��͹�����һ���ַ�������CRLF��β����
     * ���ǲ�����CRLF��
     * �˷����ڽ��������к���Ϣͷʱʹ��
     * @return
     */
    private String readLine() throws IOException {
        int d= -1;
        char c1='a';
        char c2='a';
        StringBuilder builder=new StringBuilder();
        while ((d=in.read())!=-1){
            c2=(char) d;
            //����ϴζ�ȡ��CR�����ζ�ȡ��LF
            if(c1==13&&c2==10){
                //ֹͣ��ȡ����
                break;
            }
            c1=c2; //���ζ�ȡ���ַ���¼Ϊ�ϴζ�ȡ
            builder.append(c2);
        }
        String line=builder.toString().trim();
        return line;
    }

    /**
     * �ṩ��ȡ���������Ϣ��һϵ��GET���������ﲻ�ṩSET��������Ϊ��ǰ
     * ��������ʾ�������ǿͻ��˷��͹��������ݣ�����˲���Ҫ�޸ġ�
     * @return
     */
    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * ��ȡָ������Ϣͷ
     * @param name
     * @return
     */
    public String getHeader(String name){
        return headers.get(name);
    }
}
