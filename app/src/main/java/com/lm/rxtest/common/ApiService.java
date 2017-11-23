package com.lm.rxtest.common;

import com.lm.rxtest.model.UserInfoModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface ApiService {
    @POST("selectUserInfo.shtml")
    //获取用户信息
    Observable<UserInfoModel> search(@Query("phone") String query, @Query("token") String token);
    //上传文件
    @Multipart
    @POST("upload")
    Observable<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
    //下载文件
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
