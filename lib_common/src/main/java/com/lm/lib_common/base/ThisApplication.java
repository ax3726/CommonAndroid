package com.lm.lib_common.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by LiMing on 2016/11/12.
 */
public class ThisApplication extends Application {
    public static Context applicationContext;
    private static ThisApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
    }

    public static ThisApplication getInstance() {
        return instance;
    }


}
