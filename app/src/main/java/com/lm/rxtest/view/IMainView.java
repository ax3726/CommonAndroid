package com.lm.rxtest.view;

import com.lm.rxtest.base.BaseView;
import com.lm.rxtest.model.UserInfoModel;

/**
 * Created by Administrator on 2017/9/21.
 */

public interface IMainView extends BaseView {
    void getUserInfo(UserInfoModel userInfoModel);
    void getUserInfo1(UserInfoModel userInfoModel);
    void login();
    void downProgress(long total,long precent);
}
