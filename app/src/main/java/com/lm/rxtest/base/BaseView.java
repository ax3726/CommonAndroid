package com.lm.rxtest.base;

import android.content.DialogInterface;

import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public interface BaseView {

    int getLayoutId();
    void showToast(String s);

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
