package com.lm.base.base;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface IBasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
