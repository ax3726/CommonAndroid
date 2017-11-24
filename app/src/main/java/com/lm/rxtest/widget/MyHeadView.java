package com.lm.rxtest.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.lm.rxtest.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class MyHeadView extends LinearLayout implements RefreshHeader {
    private Context mContext;
    public MyHeadView(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public MyHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }
    private void init()
    {
        LayoutInflater.from(mContext).inflate( R.layout.my_head_layout,this,true);

    }
    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {

    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
