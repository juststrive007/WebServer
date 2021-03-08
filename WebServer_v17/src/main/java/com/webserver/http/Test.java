package com.webserver.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String uri="/myweb/reg?username=%E7%8E%8B%E7%8C%9B&password=111&nickname=222&age=333";
        uri= URLDecoder.decode(uri,"UTF-8");
        System.out.println(uri);
    }
}
