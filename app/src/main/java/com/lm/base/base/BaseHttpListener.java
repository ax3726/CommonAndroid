package com.lm.base.base;



/**
 * Created by Administrator on 2018/4/20.
 * 接口请求回调   数据沟通接口
 */

public interface BaseHttpListener {

    void showWaitDialog();
    void hideWaitDialog();
    void showToast(String str);

}
