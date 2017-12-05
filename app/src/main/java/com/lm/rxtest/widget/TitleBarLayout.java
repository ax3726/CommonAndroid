package com.lm.rxtest.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.lm.rxtest.R;
import com.lm.rxtest.databinding.ComonTarbarLayoutBinding;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class TitleBarLayout extends LinearLayout {
    private Context mContext = null;
    private ComonTarbarLayoutBinding mBinding;

    public TitleBarLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TitleBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.comon_tarbar_layout, this, true);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//设置阴影
            mBinding.flyLine.setElevation(15f);
        }*/
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        mBinding.tvTitle.setText(title);
    }

    /**
     * 设置左边的图片
     */
    public void setLeftImg(int res) {
        mBinding.idLeftBtnImg.setImageResource(res);
    }

    /**
     * 设置右边的图片
     */
    public void setRightImg(int res) {

        mBinding.imgRight.setVisibility(VISIBLE);
        mBinding.imgRight.setImageResource(res);
    }

    /**
     * 设置设置更多图片
     */
    public void setMoreImg(int res) {
        mBinding.llyMore.setVisibility(VISIBLE);
        mBinding.imgMore.setImageResource(res);
    }

    /**
     * 设置右边的文字
     */
    public void setRightTxt(String txt) {
        mBinding.tvRight.setVisibility(VISIBLE);
        mBinding.tvRight.setText(txt);
    }

    /**
     * 设置左边的点击事件
     *
     * @param clickListener
     */
    public void setLeftListener(OnClickListener clickListener) {
        mBinding.llyLeft.setOnClickListener(clickListener);
    }

    /**
     * 设置右边的点击事件
     *
     * @param clickListener
     */
    public void setRightListener(OnClickListener clickListener) {
        mBinding.llyRight.setOnClickListener(clickListener);
    }

    /**
     * 设置更多的点击事件
     *
     * @param clickListener
     */
    public void setMoreListener(OnClickListener clickListener) {
        mBinding.llyMore.setOnClickListener(clickListener);
    }


}
