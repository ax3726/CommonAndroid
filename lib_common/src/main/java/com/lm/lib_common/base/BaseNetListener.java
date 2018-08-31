package com.lm.lib_common.base;


import com.lm.lib_common.net.ex.ApiException;
import com.lm.lib_common.net.ex.ResultException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/4/20.
 * 接口回调处理类
 */

public abstract class BaseNetListener<T> implements Subscriber<T> {
    private Subscription subscription;
    private BaseHttpListener baseHttpListener;
    public BaseNetListener(BaseHttpListener baseHttpListener) {
        this.baseHttpListener=baseHttpListener;
    }
    public BaseNetListener(BaseHttpListener baseHttpListener, boolean bl) {
        this.baseHttpListener=baseHttpListener;
        if ( this.baseHttpListener!=null&& bl) {
            this.baseHttpListener.showWaitDialog();
        }
    }
    @Override
    public void onSubscribe(Subscription s) {
        subscription = s;
        s.request(1); //请求一个数据
    }

    @Override
    public void onComplete() {
        subscription.cancel(); //取消订阅
        if ( this.baseHttpListener!=null) {
            this.baseHttpListener.hideWaitDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        String err_msg="";
        if (e instanceof HttpException) {
            err_msg="网络错误";
        } else if (e instanceof ApiException) {
            err_msg="Aip异常";
        } else if (e instanceof SocketTimeoutException) {
            err_msg="连接服务器超时";
        } else if (e instanceof ConnectException) {
            err_msg="未能连接到服务器";
        } else if (e instanceof ResultException) {
            ResultException resultException= (ResultException) e;
            if (resultException.getErrCode()==1001) {//token失效 需要重新登录

            }
            err_msg=e.getMessage();
        } else {
            err_msg="未知错误";
        }
        if ( this.baseHttpListener!=null) {
            this.baseHttpListener.hideWaitDialog();
            this.baseHttpListener.showToast(err_msg);
        }
        onFail(err_msg);
    }

    @Override
    public void onNext(T t) {
        //处理完后，再请求一个数据
        subscription.request(1);
        onSuccess(t);
    }
    public abstract void onSuccess(T t);
    public abstract void onFail(String errMsg);

}
