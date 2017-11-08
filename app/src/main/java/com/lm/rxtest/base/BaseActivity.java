package com.lm.rxtest.base;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by zk on 2017/6/1.
 * Description:
 */

public abstract class BaseActivity<P extends BasePresenter,B extends ViewDataBinding> extends RxAppCompatActivity implements BaseView {

    protected P mPresenter;
    protected B mBinding;
    protected AppCompatActivity aty;
    /**
     * 加载进度
     */
    private LoadingDialog mLoadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aty = this;
        mBinding= DataBindingUtil.inflate(getLayoutInflater(),getLayoutId(),null,false);
        setContentView(mBinding.getRoot());
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initView(savedInstanceState);
        initData();
        initEvent();
    }

    public Observable RxViewClick(View view)
    {
       return  RxView.clicks(view)
               .throttleFirst(500L, TimeUnit.MILLISECONDS);
    }

    protected void initEvent() {

    }

    protected void initView(Bundle savedInstanceState) {

    }

    protected void initData() {

    }


    @Override
    public void showToast(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showToast(final int id) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, getResources().getString(id), Toast.LENGTH_SHORT).show();
            }
        });

    }


    protected abstract P createPresenter();

    /**
     * 显示（默认不可取消）
     *
     * @param message 消息
     * @return
     */

    public LoadingDialog showWaitDialog(String message) {
        return showWaitDialog(message, true, null);
    }

    @Override
    public LoadingDialog showWaitDialog(boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        return showWaitDialog("", isCancel, cancelListener);
    }


    /**
     * 显示（默认不可取消）
     *
     * @return
     */

    public LoadingDialog showWaitDialog() {
        return showWaitDialog("", true, null);
    }

    /**
     * 显示
     *
     * @param message        消息
     * @param isCancel       是否可取消
     * @param cancelListener 取消监听
     * @return
     */

    public LoadingDialog showWaitDialog(String message, boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, message);
        }

        mLoadingDialog.setCancelable(isCancel);
        if (isCancel == true && cancelListener != null) {
            mLoadingDialog.setOnCancelListener(cancelListener);
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }

        return mLoadingDialog;
    }
    /***************************************************************************
     * 弹出窗方法
     ***************************************************************************/
    /**
     * 隐藏
     */
    public void hideWaitDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
