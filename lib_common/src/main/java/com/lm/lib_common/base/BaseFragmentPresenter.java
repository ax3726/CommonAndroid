package com.lm.lib_common.base;



import com.lm.lib_common.net.RetryWithDelayFunction;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public class BaseFragmentPresenter<V extends BaseFragmentView> implements IBaseFragmentPresenter<V>, LifecycleProvider<FragmentEvent>,BaseHttpListener {


    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    /**
     * 检测是否关联
     *
     * @return
     */
    protected boolean isViewAttach() {
        return mView != null;
    }

    /**
     * 获取接口
     *
     * @return
     */
    protected V getView() {
        return mView;
    }


    public <T> FlowableTransformer<T, T> callbackOnIOToMainThread() {
        return (Flowable<T> upstream) -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(RetryWithDelayFunction.create())
                .filter(t -> BaseFragmentPresenter.this.isViewAttach())
                .compose(BaseFragmentPresenter.this.bindToLifecycle());
    }

    @NonNull
    @Override
    public Observable<FragmentEvent> lifecycle() {
        return getView().lifecycle();
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event) {
        return getView().bindUntilEvent(event);
    }

    @NonNull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return getView().bindToLifecycle();
    }

    @Override
    public void showWaitDialog() {
        if (isViewAttach()) {
            getView().showWaitDialog();
        }

    }

    @Override
    public void hideWaitDialog() {
        if (isViewAttach()) {
            getView().hideWaitDialog();
        }
    }

    @Override
    public void showToast(String str) {
        if (!isViewAttach()) {
            return;
        }
        getView().showToast(str);
    }



}
