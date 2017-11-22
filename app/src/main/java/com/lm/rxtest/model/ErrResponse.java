package com.lm.rxtest.model;

/**
 * Created by lm on 2017/11/22.
 * Description：错误信息
 */
public class ErrResponse
{
    private static final String TAG = "ErrResponse";
    private int code;
    private String msg;
    private String data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }
}
