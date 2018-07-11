package com.lm.base.view;


import com.lm.base.model.UserInfoModel;
import com.lm.lib_common.base.BaseView;

/**
 * Created by Administrator on 2017/9/21.
 */

public interface IMainView extends BaseView {
    void getUserInfo(UserInfoModel userInfoModel);//获取

    void getUserInfo1(UserInfoModel userInfoModel);

    void login();

    void downProgress(long total, long precent);
}
