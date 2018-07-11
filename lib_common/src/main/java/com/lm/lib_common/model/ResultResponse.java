package com.lm.lib_common.model;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class ResultResponse {

    private String ReturnMessage;
    private int ReturnCode;

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
}
