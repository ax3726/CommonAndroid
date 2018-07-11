package com.lm.lib_common.net;


import com.lm.lib_common.base.BaseView;

import io.reactivex.FlowableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */
public class RxUtils {
    /**
     * @return 触发线程在子线程  io线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                ;
    }

    /**
     * @return 触发线程在子线程  io线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(final BaseView view) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .filter(t -> {
                    if (view != null) return true;
                    else return false;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 可自定义触发线程
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper(final Scheduler scheduler) {
        return upstream -> upstream.subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
