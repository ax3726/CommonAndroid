package com.lm.lib_common.model;

/**
 * Created by Administrator on 2017/9/21.
 */

public class BaseBean<T> {

    private String ReturnMessage;
    private int ReturnCode;
    private T ReturnData;

    public String getReturnMessage() {
        return ReturnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        ReturnMessage = returnMessage;
    }

    public int getReturnCode() {
        return ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        ReturnCode = returnCode;
    }

    public T getReturnData() {
        return ReturnData;
    }

    public void setReturnData(T returnData) {
        ReturnData = returnData;
    }
}
