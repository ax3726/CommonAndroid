package com.lm.rxtest.base;

import com.lm.rxtest.net.ex.ApiException;
import com.lm.rxtest.net.ex.ResultException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/12/25 0025.
 */

public abstract class BaseNetSubscriber<T> extends Subscriber<T> {
    private BaseView mView;

    public BaseNetSubscriber() {
    }

    public BaseNetSubscriber(BaseView baseView) {
        mView = baseView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mView != null) {
            //  mView.showProgress();
        }
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.hideWaitDialog();
        }
    }


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
         /*   if (!isViewAttach()) {
                return;
            }*/
        mView.hideWaitDialog();
        if (e instanceof HttpException) {
            mView.showToast("网络错误");
        } else if (e instanceof ApiException) {
            mView.showToast("Aip异常");
        } else if (e instanceof SocketTimeoutException) {
            mView.showToast("连接服务器超时");
        } else if (e instanceof ConnectException) {
            mView.showToast("未能连接到服务器");
        } else if (e instanceof ResultException) {
            mView.showToast(e.getMessage());
        } else {
            mView.showToast("未知错误");
        }
    }

    @Override
    public void onNext(T t) {

    }
}
