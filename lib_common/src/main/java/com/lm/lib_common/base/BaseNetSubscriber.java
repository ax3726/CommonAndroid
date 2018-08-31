package com.lm.lib_common.base;

import com.lm.lib_common.net.ex.ApiException;
import com.lm.lib_common.net.ex.ResultException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public abstract class BaseNetSubscriber<T> implements Subscriber<T> {
    private Subscription subscription;
    private BaseActivity aty;

    public BaseNetSubscriber() {

    }
    public BaseNetSubscriber(BaseActivity aty) {
        this.aty=aty;
    }
    public BaseNetSubscriber(BaseActivity aty, boolean  bl) {
        this.aty=aty;
        if (aty!=null&&bl) {
            aty.showWaitDialog();
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
        if (aty != null) {
            aty.hideWaitDialog();
        }
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (aty == null) {
            return;
        }
        aty.hideWaitDialog();
        if (e instanceof HttpException) {
            aty.showToast("网络错误");
        } else if (e instanceof ApiException) {
            aty.showToast("Aip异常");
        } else if (e instanceof SocketTimeoutException) {
            aty.showToast("连接服务器超时");
        } else if (e instanceof ConnectException) {
            aty.showToast("未能连接到服务器");
        } else if (e instanceof ResultException) {
            aty.showToast(e.getMessage());
        } else {
            aty.showToast("未知错误");
        }
    }

    @Override
    public void onNext(T t) {
        //处理完后，再请求一个数据
        subscription.request(1);
    }
}
