package com.webserver.http;

import javax.xml.ws.spi.http.HttpContext;
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

    //��uri�����󲿷֣�����ࣩ
    private String requestURI;
    //��uri�еĲ������֣����Ҳࣩ
    private String queryString;
    //�������ֵ
    private Map<String,String> parameters=new HashMap<>();

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
            //��һ������URL
            parseURI();


        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("HttpRequest:�������������");
    }

    private void parseURI(){
        System.out.println("HttpRequest:��һ������url");
        /**
         * uri��ֵ������û��Ĳ�ͬ�����в�ͬ�����
         * ���в����򲻺��в���
         *
         * ��������������磺
         * /myweb/index.html
         * /myweb/logo.png
         *
         * ���в���������磺
         * /myweb/reg?username=xx&password=xxx&...
         *
         * ���ں��в����������������Ҫ��һ������uri
         * ʵ��˼·��
         * 1�������ж�uri�Ƿ��в������Ƿ��С�������
         * ��������в�������ֱ�ӽ�uri��ֵ��ֵ��requrestURI���ɣ��������账��
         * 2.������в�������Ҫ�Ƚ�uri���ա��������Ϊ�������֣����󲿷ֺͲ������֣�
         * Ȼ�����󲿷ָ�ֵ��requestURI
         * �������ָ�ֵ��queryString
         * 3.��һ�����queryString
         * ���䰴�ա�&�����Ϊ���ɸ�������ÿ�������ٰ��ա�="���Ϊ�����������ֵ������Ϊkey��
         * value����parameters���Map����ɽ���
         */

        if(!uri.contains("?")){
            System.out.println("����");
            requestURI=uri;

        }else{
            String regex="\\?";
            String[] str=uri.split(regex);
            requestURI=str[0].trim();
            if (str.length>1) {
                queryString = str[1].trim();
                //���в������
                parseParameters();
            }

        }


        System.out.println("requrestURI:"+requestURI);
        System.out.println("queryString:"+queryString);
        System.out.println("parameters"+parameters);
        System.out.println("HttpRequest:��һ������url���");

    }

    /**
     * jie
     */
    private void parseParameters(){
        String regex="&";
        String[] parameterStrings=queryString.split(regex);
        for(String paramete:parameterStrings){
            //ÿ��������=��֣�
            String[] index=paramete.split("=");
            String key=index[0];
            if(index.length>1){
                String value=index[1];
                parameters.put(key,value);
            }else {
                parameters.put(key,null);
            }

        }
    }
    /**
     *
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
            if(c1== HTTPContext.CR && c2 == HTTPContext.LF){
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

    public String getRequestURI() {
        return requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String name){
        return parameters.get(name);
    }
}
