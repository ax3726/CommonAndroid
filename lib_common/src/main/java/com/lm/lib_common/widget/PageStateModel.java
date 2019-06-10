package com.lm.lib_common.widget;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.lm.lib_common.R;
import com.lm.lib_common.base.EmptyException;
import com.lm.lib_common.base.EmptyState;
import com.lm.lib_common.base.ThisApplication;


/**
 * 页面描述：状态页面设置模型
 * <p>
 * Created by ditclear on 2017/4/5.
 */

public class PageStateModel extends BaseObservable {

    public PageStateModel()
    {}
    public PageStateModel(Context context) {
        mContext = context;
    }

    private Context mContext= ThisApplication.getInstance();

    @EmptyState
    private int emptyState = EmptyState.NORMAL;

    private boolean empty;

    public int getEmptyState() {
        return emptyState;
    }

    /**
     * 设置状态
     *
     * @param emptyState
     */
    public void setEmptyState(@EmptyState int emptyState) {
        this.emptyState = emptyState;
        notifyChange();

    }

    private String mExpand = "您还没有添加常用地址呢!";
    private int mRes = R.drawable.no_data_icon;

    public void setExpandRes(String msg, int res) {
        mExpand = msg;
        mRes = res;
    }

    public void setErrorType(String errorType) {
        switch (errorType) {
            case "404":
                setEmptyState(EmptyState.EMPTY);
                break;
            case "309":
                setEmptyState(EmptyState.NET_ERROR);
                break;
            default:
                setEmptyState(EmptyState.EMPTY);
                break;
        }
    }

    /**
     * 显示进度条
     *
     * @return
     */
    public boolean isProgress() {
        return this.emptyState == EmptyState.PROGRESS;
    }

    /**
     * 根据异常显示状态
     *
     * @param e
     */
    public void bindThrowable(Throwable e) {
        if (e instanceof EmptyException) {
            @EmptyState
            int code = ((EmptyException) e).getCode();

            setEmptyState(code);
        }
    }

    public boolean isEmpty() {
        return this.emptyState != EmptyState.NORMAL;
    }

    /**
     * 空状态信息
     *
     * @return
     */
    @Bindable
    public String getCurrentStateLabel() {

        switch (emptyState) {
            case EmptyState.EMPTY:
                return mContext.getString(R.string.no_data);
            case EmptyState.NET_ERROR:
                return mContext.getString(R.string.please_check_net_state);
            case EmptyState.NOT_AVAILABLE:
                return mContext.getString(R.string.server_not_avaliabe);
            case EmptyState.EXPAND:
                return mExpand;

            default:
                return mContext.getString(R.string.no_data);
        }
    }

    /**
     * 空状态图片
     *
     * @return
     */
    @Bindable
    public Drawable getEmptyIconRes() {
        switch (emptyState) {
            case EmptyState.EMPTY:
                return ContextCompat.getDrawable(mContext, R.drawable.no_data_icon);
            case EmptyState.NET_ERROR:
                return ContextCompat.getDrawable(mContext, R.drawable.no_data_icon);
            case EmptyState.NOT_AVAILABLE:
                return ContextCompat.getDrawable(mContext, R.drawable.no_data_icon);
            case EmptyState.EXPAND:
                return ContextCompat.getDrawable(mContext, mRes);
            default:
                return ContextCompat.getDrawable(mContext, R.drawable.no_data_icon);
        }
    }

    public void onStateClick(View view) {
        if (mIOnClickListener != null) {
            mIOnClickListener.click(view);
        }
    }

    private IOnClickListener mIOnClickListener;

    public void setIOnClickListener(IOnClickListener mIOnClickListener) {
        this.mIOnClickListener = mIOnClickListener;
    }

    public interface IOnClickListener {
        void click(View view);
    }
}
