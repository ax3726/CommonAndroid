package com.lm.base.base;

/**
 * 页面描述：异常
 * <p>
 * Created by ditclear on 2017/4/5.
 */
public class EmptyException extends Exception {

    private int code;

    public EmptyException(@EmptyState int code) {
        super();
        this.code = code;
    }


    @EmptyState
    public int getCode() {
        return code;
    }

    public void setCode(@EmptyState int code) {
        this.code = code;
    }
}