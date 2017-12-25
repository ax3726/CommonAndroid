package com.lm.rxtest.base;

import android.support.annotation.NonNull;

import com.lm.rxtest.net.RetryWithDelayFunc1;
import com.lm.rxtest.net.ex.ApiException;
import com.lm.rxtest.net.ex.ResultException;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class BasePresenter<V extends BaseView> implements IBasePresenter<V>, ActivityLifecycleProvider {


    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    /**
     * 检测是否关联
     *
     * @return
     */
    protected boolean isViewAttach() {
        return mView != null ;
    }

    /**
     * 获取接口
     *
     * @return
     */
    protected V getView() {
        return mView;
    }

    @NonNull
    @Override
    public Observable<ActivityEvent> lifecycle() {
        return getView().lifecycle();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return getView().bindUntilEvent(event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return getView().bindToLifecycle();
    }


    public <T> Observable.Transformer<T, T> callbackOnIOToMainThread() {
        return tObservable -> (Observable<T>) tObservable.subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelayFunc1.create())
                .filter(t -> BasePresenter.this.isViewAttach()).observeOn(AndroidSchedulers.mainThread()).compose(BasePresenter.this.bindToLifecycle());
    }

    public abstract class BaseNetSubscriber<T> extends Subscriber<T> {


        @Override
        public void onStart() {
            super.onStart();
            if (isViewAttach()) {
                // getView().showProgress();
            }
        }

        @Override
        public void onCompleted() {
            if (isViewAttach()) {
                getView().hideWaitDialog();
            }
        }


        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            if (!isViewAttach()) {
                return;
            }
            getView().hideWaitDialog();
            if (e instanceof HttpException) {
                getView().showToast("网络错误");
            } else if (e instanceof ApiException) {
                getView().showToast("Aip异常");
            } else if (e instanceof SocketTimeoutException) {
                getView().showToast("连接服务器超时");
            } else if (e instanceof ConnectException) {
                getView().showToast("未能连接到服务器");
            } else if (e instanceof ResultException) {
                getView().showToast(e.getMessage());
            } else {
                getView().showToast("未知错误");
            }
        }

        @Override
        public void onNext(T t) {

        }
    }


}
