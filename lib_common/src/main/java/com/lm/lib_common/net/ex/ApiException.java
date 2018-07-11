package com.lm.lib_common.net.ex;


import com.lm.lib_common.model.ResponseCodeEnum;

/**
 * Created by jhhuang on 2016/9/19.
 * QQ:781913268
 * Description：联网约定异常
 */
public class ApiException extends RuntimeException
{
    private static final String TAG = "ApiException";
    private ResponseCodeEnum responseCode;

    public ResponseCodeEnum getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(ResponseCodeEnum responseCode)
    {
        this.responseCode = responseCode;
    }

    public ApiException(ResponseCodeEnum responseCode)
    {
        super(responseCode.getText());
        this.responseCode = responseCode;
    }

}
