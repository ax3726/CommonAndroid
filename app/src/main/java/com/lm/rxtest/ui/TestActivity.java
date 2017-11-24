package com.lm.rxtest.ui;

import com.lm.rxtest.R;
import com.lm.rxtest.base.BaseActivity;
import com.lm.rxtest.base.BasePresenter;
import com.lm.rxtest.databinding.ActivityTestBinding;

public class TestActivity extends BaseActivity<BasePresenter, ActivityTestBinding> {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }


    @Override
    protected void initData() {
        super.initData();
        setSlideable(false);


    }
}
