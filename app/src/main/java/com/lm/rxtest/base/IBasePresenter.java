package com.lm.rxtest.base;

/**
 * Created by zk on 2017/6/1.
 * Description:
 */

public interface IBasePresenter<V extends BaseView> {
    void attachView(V view);
    void detachView();
}
