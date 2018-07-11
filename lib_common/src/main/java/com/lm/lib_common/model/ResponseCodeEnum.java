package com.lm.lib_common.model;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */
public enum ResponseCodeEnum {
    FAILURE(0, "失败"),
    PARAM_ERR(1001, "参数错误"),
    SUCCESS(2000, "成功"),
    NODATA(3000, "没有数据"),
    AUTH_FAILURE(4000, "账号验证异常，请重新登陆"),
    AUTH_CONFLICT(4001, "在其他设备登陆,请重新登陆"),
    SERVER_ERR(5000, "服务器错误"),
    ILLEGAL_REQUEST(5001, "非法请求"),
    NOT_FOUND(5002, "资源不存在"),
    REFLECT_ERR(6001, "服务器配置错误"),

    ;

    private Integer value;
    private String text;

    ResponseCodeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
