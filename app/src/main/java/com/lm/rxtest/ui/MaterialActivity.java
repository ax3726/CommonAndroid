package com.lm.rxtest.ui;

import android.view.View;

import com.lm.rxtest.R;
import com.lm.rxtest.base.BaseActivity;
import com.lm.rxtest.base.BasePresenter;
import com.lm.rxtest.databinding.ActivityMaterialBinding;

public class MaterialActivity extends BaseActivity<BasePresenter, ActivityMaterialBinding> {
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
        return R.layout.activity_material;
    }

    @Override
    protected void initData() {
        super.initData();
        mBinding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
