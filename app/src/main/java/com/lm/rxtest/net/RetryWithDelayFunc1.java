package com.lm.rxtest.net;


import com.lm.rxtest.net.ex.ApiException;
import com.lm.rxtest.net.ex.ResultException;
import com.lm.rxtest.model.ResponseCodeEnum;

import rx.Observable;
import rx.functions.Func1;


/**
 * Created by lm on 2017/11/22.
 * Description：
 */
public class RetryWithDelayFunc1 implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private static final String TAG = "RetryWithDelayFunc1";

    public static RetryWithDelayFunc1 create() {
        return new RetryWithDelayFunc1();
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> error) {
        return error.flatMap(new Func1<Throwable, Observable<?>>() {
            @Override
            public Observable<?> call(Throwable throwable) {
                if (throwable instanceof ApiException) {
                    ResponseCodeEnum responseCode = ((ApiException) throwable).getResponseCode();
                    switch (responseCode) {
                        case NODATA:
                            return Observable.error(throwable);
                        default:
                            return Observable.error(throwable);
                    }
                }

                if (throwable instanceof ResultException) {
                    return Observable.error(throwable);
                }
                return Observable.error(throwable);
            }
        });
    }


}
