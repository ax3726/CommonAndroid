package com.lm.rxtest.ui;

import android.animation.ObjectAnimator;
import android.util.Log;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.lm.rxtest.R;
import com.lm.rxtest.base.BaseActivity;
import com.lm.rxtest.databinding.ActivityMainBinding;
import com.lm.rxtest.model.UserInfoModel;
import com.lm.rxtest.prestener.MainPrestener;
import com.lm.rxtest.view.IMainView;
import com.lm.rxtest.widget.ColorEvaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPrestener, ActivityMainBinding> implements IMainView {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPrestener createPresenter() {
        return new MainPrestener();
    }

    @Override
    protected void initData() {


        ObjectAnimator anim2 = ObjectAnimator.ofObject(mBinding.txt, "textColor", new ColorEvaluator(),
                "#0000FF", "#FF0000");

        anim2.setDuration(3000);

        anim2.start();


        mBinding.springProgressView.setMaxCount(100);
        mBinding.springProgressView.setCurrentCount(99);
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            list.add("项" + i);
        }
        Observable.from(list)
                .subscribe(s -> Log.e("msg", s));

        RxTextView.textChanges(mBinding.etTxt)
                .filter(charSequence -> !"11".equals(charSequence.toString()))
                .map(charSequence -> "黎明" + charSequence)
                .subscribe(str -> showToast(str));

        RxViewClick(mBinding.btn)
                .subscribe(aVoid -> {
                    showWaitDialog();
                    Observable.timer(1000, TimeUnit.MILLISECONDS)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> mPresenter.getUserInfo());


                });


    }

    @Override
    public void getUserInfo(UserInfoModel userInfoModel) {
        showToast("获取到了!");
    }

    @Override
    public void login() {
        showToast("跳转主界面");
    }


}
