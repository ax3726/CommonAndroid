package com.lm.rxtest.common;

import com.lm.rxtest.net.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2017/9/21.
 */

public class Api {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    private static ApiService apiService;

    public static ApiService getApi() {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // .addInterceptor(new LoggerInterceptor("wdw", true))
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(chain -> {//添加请求头部
                        Request originalRequest = chain.request();
                        Request authorised = originalRequest.newBuilder()
                                .header("Authorization", "token")
                                .build();
                        return chain.proceed(authorised);
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Link.SEREVE)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
