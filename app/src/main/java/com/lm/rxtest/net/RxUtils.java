package com.lm.rxtest.net;


import com.lm.rxtest.base.BaseView;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zk on 2017/8/21.
 * Description:
 */
public class RxUtils
{
    /**
     *
     * @return 触发线程在子线程  io线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper()
    {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .retryWhen((Function<? super Flowable<Throwable>, ? extends Publisher<?>>) RetryWithDelayFunc1.create())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     * @return 触发线程在子线程  io线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(final BaseView view)
    {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .retryWhen((Function<? super Flowable<Throwable>, ? extends Publisher<?>>) RetryWithDelayFunc1.create())
                .filter(t -> {
                    if (view != null) return true;
                    else return false;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 可自定义触发线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(final Scheduler scheduler)
    {
        return tObservable -> tObservable.subscribeOn(scheduler)
                .retryWhen((Function<? super Flowable<Throwable>, ? extends Publisher<?>>) RetryWithDelayFunc1.create())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
