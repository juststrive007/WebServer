package com.webserver.http;

/**
 * 空请求异常
 * 当客户端连接后发送了空请求时，HttpRequest会抛出该异常
 * @author wm
 * 1.继承exception
 */
public class EmptyRequestException extends Exception{

    private static final long serialVersionUID = 2210614983165114653L;

    public EmptyRequestException() {
    }

    public EmptyRequestException(String message) {
        super(message);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }

    public EmptyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
