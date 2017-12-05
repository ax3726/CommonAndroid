package com.lm.rxtest.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.lm.rxtest.R;
import com.lm.rxtest.base.slide.SlideBackActivity;
import com.lm.rxtest.databinding.WidgetLayoutEmptyBinding;
import com.lm.rxtest.widget.TitleBarLayout;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by lm on 2017/11/22.
 * Description:
 */

public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends SlideBackActivity implements BaseView {

    protected P mPresenter;
    protected B mBinding;
    protected AppCompatActivity aty;
    /**
     * 加载进度
     */
    private LoadingDialog mLoadingDialog;

    protected TitleBarLayout mTitleBarLayout = null;//头部控件

    protected StateModel mStateModel = new StateModel();//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//添加沉浸式状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            //getWindow().setStatusBarColor(color);
        }
        if (isPrestener()) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        aty = this;
        setActivityView();
        initTitleBar();
        initView(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 设置activity视图
     */
    private void setActivityView() {
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        if (isTitleBar()) {
            mTitleBarLayout = new TitleBarLayout(aty);
            LinearLayout lly = new LinearLayout(aty);
            lly.setOrientation(LinearLayout.VERTICAL);
            lly.addView(mTitleBarLayout);
            FrameLayout fly = new FrameLayout(aty);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.widget_layout_empty, null, false);
            emptyBinding.setStateModel(mStateModel);
            fly.addView(emptyBinding.getRoot());
            lly.addView(fly);
            fly.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            setContentView(lly);
            mTitleBarLayout.setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            FrameLayout fly = new FrameLayout(aty);
            fly.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
            fly.addView(mBinding.getRoot());
            WidgetLayoutEmptyBinding emptyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.widget_layout_empty, null, false);
            emptyBinding.setStateModel(mStateModel);
            fly.addView(emptyBinding.getRoot());
            setContentView(fly);
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

    public Observable RxViewClick(View view) {
        return RxView.clicks(view)
                .throttleFirst(500L, TimeUnit.MILLISECONDS);
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
        return true;
    }

    /**
     * 是否加载Prestener
     *
     * @return
     */
    protected boolean isPrestener() {
        return true;
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
        if (isPrestener()) {
            mPresenter.detachView();
        }
    }
}
