package com.lm.lib_common.base;

/**
 * 页面描述：异常
 * <p>
 * Created by ditclear on 2017/4/5.
 */
public class EmptyException extends Exception {

    private int code;

    public EmptyException(@com.lm.lib_common.base.EmptyState int code) {
        super();
        this.code = code;
    }


    @com.lm.lib_common.base.EmptyState
    public int getCode() {
        return code;
    }

    public void setCode(@com.lm.lib_common.base.EmptyState int code) {
        this.code = code;
    }
}