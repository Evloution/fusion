package com.elink.fusion.bean;

/**
 * Creation Time: 2018/8/20 10:44.
 * Author: King.
 * Description: 服务端的异常处理类，根据与服务端约定的code判断
 */
public class ApiException extends RuntimeException{

    private int errorCode;
    private String msg;
    private String data;

    public ApiException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
