package ml.gsy.com.library.widget.slide;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ml.gsy.com.library.adapters.RecyclerWrapAdapter;


/**
 * Created by Administrator on 2017/7/28.
 */

public class SlideRecycleView extends RecyclerView {

    public SlideRecycleView(Context context) {
        super(context);
    }

    public SlideRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private ArrayList<View> mHeaderViews = new ArrayList<>();

    private ArrayList<View> mFootViews = new ArrayList<>();

    private Adapter mAdapter;

    public void addHeaderView(View view) {
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        mHeaderViews.clear();
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
//                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void addFootView(View view) {
        mFootViews.clear();
        mFootViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, mAdapter);
//                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
            super.setAdapter(adapter);
        } else {
            adapter = new RecyclerWrapAdapter(mHeaderViews, mFootViews, adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }

    private int mheight = 0;
    private View mSlideView = null;

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
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        View viewByPosition = getLayoutManager().findViewByPosition(0);
        if (viewByPosition != null) {
            int y = viewByPosition.getTop();
            if (mISlideScrollListener != null) {
                mISlideScrollListener.onScrollChange(y);
            }
            if (y < 0) {
                y = -y;
            }
            if (y >= mheight) {
                mSlideView.setVisibility(View.VISIBLE);
            } else {
                mSlideView.setVisibility(View.GONE);
            }

            if (y <= 0) {
                if (mISlideScrollListener != null) {
                    mISlideScrollListener.onScrollState(SlideState.HIDE);
                }
            } else if (y > 0 && y <= mheight) {
                float scale = (float) y / mheight;
                if (mISlideScrollListener != null) {
                    mISlideScrollListener.onScrollState(SlideState.SHOW);
                    mISlideScrollListener.onScrollScale(scale);
                }
                //   float alpha = (255 * scale);
            } else {
                if (mISlideScrollListener != null) {
                    mISlideScrollListener.onScrollState(SlideState.SHOW);
                }
            }

        } else {
            mSlideView.setVisibility(View.VISIBLE);
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
