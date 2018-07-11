package com.lm.lib_common.net;


import android.util.Log;


import com.lm.lib_common.model.ResponseCodeEnum;
import com.lm.lib_common.net.ex.ApiException;
import com.lm.lib_common.net.ex.ResultException;


import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.HttpException;


/**
 * Created by lm on 2017/11/22.
 * Description：
 */
public class RetryWithDelayFunction implements Function<Flowable<Throwable>, Publisher<?>> {
    private static final String TAG = "RetryWithDelayFunction";

    public static RetryWithDelayFunction create() {
        return new RetryWithDelayFunction();
    }

    @Override
    public Publisher<?> apply(@NonNull Flowable<Throwable> throwableFlowable) throws Exception {
        return throwableFlowable.flatMap(throwable -> {
            if (throwable instanceof ApiException) {
                ResponseCodeEnum responseCode = ((ApiException) throwable).getResponseCode();
                switch (responseCode) {
                    case NODATA:
                        return Flowable.error(throwable);
                    default:
                        return Flowable.error(throwable);
                }
            }
            if (throwable instanceof HttpException) {//http异常
                HttpException httpException = (HttpException) throwable;

                Log.e("msg", httpException.code() + httpException.message());
            }


            if (throwable instanceof ResultException) {
                return Flowable.error(throwable);
            }
            return Flowable.error(throwable);
        });
    }
}
