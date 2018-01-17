package com.lm.base.model;

/**
 * Created by Administrator on 2017/9/21.
 */

public class BaseBean<T> {

    private String info;
    private int status;
    private T data;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
