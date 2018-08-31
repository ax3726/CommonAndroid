package com.lm.lib_common.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 页面描述：空状态
 * <p>
 * Created by Administrator on 2017/4/5.
 */
@IntDef({EmptyState.NORMAL, EmptyState.PROGRESS, EmptyState.EMPTY, EmptyState.NET_ERROR, EmptyState.NOT_AVAILABLE, EmptyState.EXPAND})
@Retention(RetentionPolicy.SOURCE)
public @interface EmptyState {

    int NORMAL = -1;  //正常
    int PROGRESS = -2;//显示进度条
    int EMPTY = 11111; //列表数据为空
    int NET_ERROR = 22222;  //网络未连接
    int NOT_AVAILABLE = 33333; //服务器不可用
    int EXPAND = 44444; //自定义的状态
    //...各种页面的空状态，可以自己定义、添加

}