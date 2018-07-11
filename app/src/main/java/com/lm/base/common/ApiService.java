package com.lm.base.common;

import com.lm.base.model.BaseBean;
import com.lm.base.model.TestModel;
import com.lm.base.model.UserInfoModel;

import java.util.HashMap;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface ApiService {
    @POST("selectUserInfo.shtml")
    //获取用户信息
    Flowable<UserInfoModel> search(@Query("phone") String query, @Query("token") String token, @Body TestModel testModel);
    @POST("selectUserInfo.shtml")

        //获取用户信息
    Flowable<UserInfoModel> search1();
    //下载文件
    @Streaming
    @GET
    Flowable<ResponseBody> download(@ Url String url);

    // 上传图片
    @Multipart
    @POST("upload")
    Flowable<BaseBean> getLoginRegisterImage(@Query("name") String name,
                                             @Query("gender") String gender,
                                             @Part MultipartBody.Part file);
}
