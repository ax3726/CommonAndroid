package com.lm.base.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.lm.base.R;
import com.lm.base.databinding.ActivityTestBinding;

import java.util.ArrayList;
import java.util.List;


import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;

public class TestActivity extends BaseActivity<BasePresenter, ActivityTestBinding> {

    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    /**
     * 不添加通用的头部
     *
     * @return
     */
    @Override
    protected boolean isTitleBar() {
        return false;
    }


    public String isEmpty(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initData() {
        super.initData();

        setSupportActionBar(mBinding.toolbar);

        mBinding.collapsingToolbarLayout.setTitle("这个是标题");
        mBinding.collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        mBinding.collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorcccccc));
        mBinding.toolbar.setSubtitle("这是标题");
        mBinding.toolbar.setNavigationIcon(R.drawable.back_icon);
        mBinding.toolbar.setTitle("标题");
        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        for (int i = 0; i < 100; i++) {
            mDataList.add("");
        }
        mAdapter = new CommonAdapter<String>(aty, R.layout.item_test, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView txt = holder.getView(R.id.tv_txt);

                txt.setText(isEmpty("我是第" + (position + 1) + "项"));


                holder.setText(R.id.tv_txt, "我是第" + (position + 1) + "项");

            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setNestedScrollingEnabled(false);
        mBinding.rcBody.setAdapter(mAdapter);

/*
        //设置 Header 样式
        mBinding.refreshLayout.setRefreshHeader(new MyHeadView(this));
        //设置 Footer 样式
      //  mBinding.refreshLayout.setRefreshFooter(new BallPulseFooter(this));
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                mBinding.refreshLayout.resetNoMoreData();
                mDataList.clear();
                for (int i = 0; i < 10; i++) {
                    mDataList.add("");
                }
                mAdapter.notifyDataSetChanged();

            }
        });

        mBinding.refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);

                for (int i = 0; i < 10; i++) {
                    mDataList.add("");
                }
                mAdapter.notifyDataSetChanged();
                if (mDataList.size()==40) {

                    mBinding.refreshLayout.finishLoadmoreWithNoMoreData();
                }

            }
        });*/

    }
}
