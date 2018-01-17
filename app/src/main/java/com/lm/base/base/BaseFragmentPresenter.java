package com.lm.base.base;


import com.lm.base.net.RetryWithDelayFunction;
import com.lm.base.net.ex.ApiException;
import com.lm.base.net.ex.ResultException;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class BaseFragmentPresenter<V extends BaseFragmentView> implements IBaseFragmentPresenter<V>, LifecycleProvider<FragmentEvent> {


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
        return mView != null;
    }

    /**
     * 获取接口
     *
     * @return
     */
    protected V getView() {
        return mView;
    }


    public <T> FlowableTransformer<T, T> callbackOnIOToMainThread() {
        return (Flowable<T> upstream) -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(RetryWithDelayFunction.create())
                .filter(t -> BaseFragmentPresenter.this.isViewAttach())
                .compose(BaseFragmentPresenter.this.bindToLifecycle());
    }

    @NonNull
    @Override
    public Observable<FragmentEvent> lifecycle() {
        return getView().lifecycle();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return getView().bindUntilEvent(event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return getView().bindToLifecycle();
    }

    public abstract class BaseNetSubscriber<T> implements Subscriber<T> {
        private Subscription subscription;

        public BaseNetSubscriber() {
        }
        public BaseNetSubscriber(boolean bl) {
            if (isViewAttach() && bl) {
                getView().showWaitDialog();
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
            //处理完后，再请求一个数据
            subscription.request(1);
        }
    }


}
