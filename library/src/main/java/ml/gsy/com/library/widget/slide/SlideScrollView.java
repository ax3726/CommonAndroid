package ml.gsy.com.library.widget.slide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/7/28.
 */

public class SlideScrollView extends ScrollView {


    public SlideScrollView(Context context) {
        super(context);
    }

    public SlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mheight = 0;
    private View mSlideView = null;
    private View mHeadView = null;

    /**
     * 滑动只悬浮位置
     */
    public void scrolltoSlide() {
        scrollTo(0, mheight);
    }


    public void refshHeight() {
        if (mHeadView != null) {
            int w = ViewGroup.MeasureSpec.makeMeasureSpec(0,
                    ViewGroup.MeasureSpec.UNSPECIFIED);
            int h = ViewGroup.MeasureSpec.makeMeasureSpec(0,
                    ViewGroup.MeasureSpec.UNSPECIFIED);
            mHeadView.measure(w, h);
            mheight = mHeadView.getMeasuredHeight();
        }
    }

    public void setHeadView(View headView) {
        int w = ViewGroup.MeasureSpec.makeMeasureSpec(0,
                ViewGroup.MeasureSpec.UNSPECIFIED);
        int h = ViewGroup.MeasureSpec.makeMeasureSpec(0,
                ViewGroup.MeasureSpec.UNSPECIFIED);
        headView.measure(w, h);
        mheight = headView.getMeasuredHeight();
    }

    public void setSlideView(View slideView) {
        mSlideView = slideView;
    }

    @Override
    protected void onScrollChanged(int mScrollX, int mScrollY, int oldX, int oldY) {
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY);
        if (mISlideScrollListener != null) {
            mISlideScrollListener.onScrollChange(mScrollY);
        }
        int y = mScrollY;
        if (y < 0) {
            y = -y;
        }
        if (y >= mheight) {
            mSlideView.setVisibility(View.VISIBLE);
            if (mISlideScrollListener != null) {
                mISlideScrollListener.onScrollState(SlideState.SHOW);
            }
        } else {
            mSlideView.setVisibility(View.GONE);
            if (mISlideScrollListener != null) {
                mISlideScrollListener.onScrollState(SlideState.HIDE);
            }
        }

        if (y <= 0) {
//            if (mISlideScrollListener != null) {
//                mISlideScrollListener.onScrollState(SlideState.HIDE);
//            }
        } else if (y > 0 && y <= mheight) {
            float scale = (float) y / mheight;
            if (mISlideScrollListener != null) {
              //  mISlideScrollListener.onScrollState(SlideState.SHOW);
                mISlideScrollListener.onScrollScale(scale);
            }
            //   float alpha = (255 * scale);
        } else {
            if (mISlideScrollListener != null) {
                mISlideScrollListener.onScrollState(SlideState.SHOW);
            }
        }
    }

    private ISlideScrollListener mISlideScrollListener;

    public void setISlideScrollListener(ISlideScrollListener mISlideScrollListener) {
        this.mISlideScrollListener = mISlideScrollListener;
    }


}
