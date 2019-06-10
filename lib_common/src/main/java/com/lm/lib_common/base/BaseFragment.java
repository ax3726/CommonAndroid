package com.lm.lib_common.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.lm.lib_common.R;
import com.lm.lib_common.databinding.WidgetLayoutEmptyBinding;
import com.lm.lib_common.net.RetryWithDelayFunction;
import com.lm.lib_common.widget.LoadingDialog;
import com.lm.lib_common.widget.PageStateModel;
import com.lm.lib_common.widget.TitleBarLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public abstract class BaseFragment<P extends BaseFragmentPresenter, B extends ViewDataBinding> extends RxFragment implements BaseFragmentView, BaseHttpListener {

    /**
     * Fragment根视图
     */
    protected View           mFragmentRootView;
    protected P              mPresenter;
    protected B              mBinding;
    protected Activity       aty;
    /**
     * 加载进度
     */
    private   LoadingDialog  mLoadingDialog;
    protected TitleBarLayout mTitleBarLayout  = null;//头部控件
    protected PageStateModel mPageStateModelm = new PageStateModel();//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aty = getActivity();
        mFragmentRootView = inflaterView(inflater, container, savedInstanceState);

        if (isPrestener()) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        initTitleBar();
        initData();
        initView(savedInstanceState);
        initEvent();
        return mFragmentRootView;
    }

    /**
     * 加载View
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @param bundle    Bundle
     * @return
     */
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        if (isTitleBar()) {
            mTitleBarLayout = new TitleBarLayout(aty);
            AutoLinearLayout lly = new AutoLinearLayout(aty);
            lly.setOrientation(LinearLayout.VERTICAL);
            lly.addView(mTitleBarLayout);
            AutoFrameLayout fly = new AutoFrameLayout(aty);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.widget_layout_empty, null, false);
            emptyBinding.setPageState(mPageStateModelm);
            fly.addView(emptyBinding.getRoot());
            lly.addView(fly, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mTitleBarLayout.setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aty.finish();
                }
            });
            return lly;
        } else {
            AutoFrameLayout fly = new AutoFrameLayout(aty);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(inflater, R.layout.widget_layout_empty, null, false);
            emptyBinding.setPageState(mPageStateModelm);
            fly.addView(emptyBinding.getRoot());
            return fly;
        }

    }


    /**
     * @param url   图片路径
     * @param img   加载图片的控件
     * @param parms 第一个  默认图片  第二个 加载错误图片
     */
    protected void loadImag(String url, ImageView img, int... parms) {
        DrawableTypeRequest<String> load = Glide.with(aty).load(url);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(img);
    }


    protected void startActivity(Class<?> cls) {
        startActivity(new Intent(aty, cls));
    }


    protected void initEvent() {

    }

    protected void initView(Bundle savedInstanceState) {

    }

    protected void initTitleBar() {

    }

    protected void initData() {

    }


    /**
     * 是否显示头部
     *
     * @return
     */
    protected boolean isTitleBar() {
        return false;
    }

    /**
     * 是否加载Prestener
     *
     * @return
     */
    protected boolean isPrestener() {
        return false;
    }


    @Override
    public void showToast(final String s) {
        aty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showToast(final int id) {

        aty.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(aty, getResources().getString(id), Toast.LENGTH_SHORT).show();
            }
        });

    }


    protected abstract int getLayoutId();

    protected abstract P createPresenter();

    /**
     * 显示（默认不可取消）
     *
     * @param message 消息
     * @return
     */

    public void showWaitDialog(String message) {
        showWaitDialog(message, true, null);
    }

    @Override
    public void showWaitDialog(boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        showWaitDialog("", isCancel, cancelListener);
    }


    /**
     * 显示（默认不可取消）
     *
     * @return
     */

    public void showWaitDialog() {
        showWaitDialog("", true, null);
    }

    /**
     * 显示
     *
     * @param message        消息
     * @param isCancel       是否可取消
     * @param cancelListener 取消监听
     * @return
     */

    public void showWaitDialog(String message, boolean isCancel, DialogInterface.OnCancelListener cancelListener) {
        if (aty == null) {
            return;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(aty, message);
        }
        mLoadingDialog.setCancelable(isCancel);
        if (isCancel == true && cancelListener != null) {
            mLoadingDialog.setOnCancelListener(cancelListener);
        }

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }


    }
    /***************************************************************************
     * 弹出窗方法
     ***************************************************************************/
    /**
     * 隐藏
     */
    public void hideWaitDialog() {
        if (aty == null) {
            return;
        }
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isPrestener()) {
            mPresenter.detachView();
        }
    }


    public <T> FlowableTransformer<T, T> callbackOnIOToMainThread() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .retryWhen(RetryWithDelayFunction.create())
                .filter(t -> aty != null).observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle());
    }

    @Override
    public void setEmptyState(@EmptyState int emptyState) {
        mPageStateModelm.setEmptyState(emptyState);
    }

}
