package com.eshanren.dto;

import java.io.Serializable;

/**
 * 响应封装
 *
 * @author WWF
 */
public class RespRet implements Serializable {

    private boolean success;
    private String message;
    private String error;
    private Object data;


    public RespRet() {
    }

    public static RespRet fail() {
        RespRet respRet = new RespRet();
        respRet.setSuccess(false);
        respRet.setMessage("fail");
        respRet.setError("fail");
        return respRet;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
