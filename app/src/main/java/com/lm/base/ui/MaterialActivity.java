package com.lm.base.ui;

import android.view.View;

import com.lm.base.R;

import com.lm.base.databinding.ActivityMaterialBinding;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;

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
