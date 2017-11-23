package com.lm.rxtest.common;

import android.app.Application;
import android.os.Environment;

import ml.gsy.com.library.utils.CacheUtils;
import ml.gsy.com.library.utils.Utils;

/**
 * Created by Administrator on 2017/11/22 0022.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    public static  String Base_Path="";
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        Base_Path = Utils.getCacheDirectory(this, Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        //缓存初始化
        CacheUtils.getInstance().init(CacheUtils.CacheMode.CACHE_MAX,
                Utils.getCacheDirectory(this, Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
    }
}
