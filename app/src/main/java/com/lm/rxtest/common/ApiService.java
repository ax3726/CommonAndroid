package com.lm.rxtest.common;

import com.lm.rxtest.model.UserInfoModel;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zk on 2017/6/1.
 * Description:
 */

public interface ApiService {
    @POST("selectUserInfo.shtml")
    //获取用户信息
    Observable<UserInfoModel> search(@Query("phone") String query, @Query("token") String token);

}
