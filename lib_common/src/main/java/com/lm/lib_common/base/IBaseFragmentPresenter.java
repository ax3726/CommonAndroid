package com.lm.lib_common.base;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface IBaseFragmentPresenter<V extends BaseFragmentView> {
    void attachView(V view);
    void detachView();
}
