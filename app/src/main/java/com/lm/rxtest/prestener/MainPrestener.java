package com.lm.rxtest.prestener;

import com.lm.rxtest.base.BasePresenter;
import com.lm.rxtest.common.Api;
import com.lm.rxtest.model.UserInfoModel;
import com.lm.rxtest.view.IMainView;

/**
 * Created by Administrator on 2017/9/21.
 */
public class MainPrestener extends BasePresenter<IMainView> {

    public void getUserInfo() {
        Api.getApi().search("15170193726", "fffffff")
                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread())
                .subscribe(new NetSubscriber<UserInfoModel>() {
                    @Override
                    public void onNext(UserInfoModel userInfoModel) {
                        super.onNext(userInfoModel);
                        getView().getUserInfo(userInfoModel);
                    }
                });
    }

    public void longin() {
        Api.getApi().search("15170193726", "fffffff")
                .compose(callbackOnIOThread())
                .compose(verifyOnMainThread()).subscribe(new NetSubscriber<UserInfoModel>() {
            @Override
            public void onNext(UserInfoModel userInfoModel) {
                super.onNext(userInfoModel);
                getView().login();//

            }
        });
    }
}
