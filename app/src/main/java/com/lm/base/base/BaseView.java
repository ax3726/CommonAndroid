package com.lm.base.base;

import android.content.DialogInterface;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.Observable;
import ml.gsy.com.library.common.LoadingDialog;


/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface BaseView {

    int getLayoutId();

    void showToast(String s);

    void setEmptyState(@EmptyState int emptyState);

    void showToast(int id);

    Observable<ActivityEvent> lifecycle();

    <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event);

    <T> LifecycleTransformer<T> bindToLifecycle();

    /**
     * 隐藏WaitDialog
     */
    void hideWaitDialog();

    /**
     * 显示
     *
     * @return
     */
    LoadingDialog showWaitDialog();


    /**
     * 显示
     *
     * @param message 消息
     * @return
     */
    LoadingDialog showWaitDialog(String message);

    /**
     * 显示
     *
     * @param isCancel       是否可取消
     * @param cancelListener 取消监听
     * @return
     */
    LoadingDialog showWaitDialog(boolean isCancel, DialogInterface.OnCancelListener cancelListener);


    /**
     * @param message        消息
     * @param isCancel       是否可取消
     * @param cancelListener 取消监听
     * @return
     */
    LoadingDialog showWaitDialog(String message, boolean isCancel, DialogInterface.OnCancelListener cancelListener);

}
