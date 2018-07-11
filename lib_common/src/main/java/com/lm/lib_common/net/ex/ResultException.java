package com.lm.lib_common.net.ex;

/**
 * Created by jhhuang on 2016/10/20.
 * QQ:781913268
 * Description：捕获服务器约定的错误类型
 */
public class ResultException extends RuntimeException
{
    private static final String TAG = "ResultException";
    private int errCode = -1;

    public ResultException(int errCode, String msg)
    {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode()
    {
        return errCode;
    }

    public void setErrCode(int errCode)
    {
        this.errCode = errCode;
    }
}
