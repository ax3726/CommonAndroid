package com.lm.rxtest.base.slide;

import android.app.Application;

/**
 * @author lihong
 * @since 2016/10/28
 */
public interface ActivityInterface {
    /**
     * Set the callback for activity lifecycle
     *
     * @param callbacks callbacks
     */
    void setActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callbacks);
}
