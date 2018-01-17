package com.lm.base.view;

import com.lm.base.base.BaseView;
import com.lm.base.model.UserInfoModel;

/**
 * Created by Administrator on 2017/9/21.
 */

public interface IMainView extends BaseView {
    void getUserInfo(UserInfoModel userInfoModel);//获取

    void getUserInfo1(UserInfoModel userInfoModel);

    void login();

    void downProgress(long total, long precent);
}
