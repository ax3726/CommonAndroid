package com.lm.lib_common.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lm.lib_common.R;


/**
 * Created by Administrator on 2016/11/14 0014.
 */
public class LoadingDialog extends Dialog {

    private TextView mTvLoadingmsg;
    private Context mContext;
    public LoadingDialog(Context context, String msg) {
        super(context, R.style.DialogBaseStyle_Loading);
        mContext=context;
        this.setContentView(R.layout.request_dialog);
        mTvLoadingmsg = (TextView) findViewById(R.id.id_tv_loadingmsg);
        if (!TextUtils.isEmpty(msg)) {
            mTvLoadingmsg.setVisibility(View.VISIBLE);
            mTvLoadingmsg.setText(msg);
        } else {
            setMsg(mMsg);
            mTvLoadingmsg.setVisibility(View.VISIBLE);
        }
        this.setCancelable(true);
    }
    private String mMsg="正在加载中请稍后";

    public void setMsg(String msg) {
      //  this.mMsg = msg;
        mTvLoadingmsg.setText(msg);
    }
}
