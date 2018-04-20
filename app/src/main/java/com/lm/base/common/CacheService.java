package com.lm.base.common;

import com.lm.base.model.UserInfoModel;

import com.lm.base.library.utils.CacheUtils;

/**
 * Created by Administrator on 2017/11/22 0022.
 * 缓存统一处理服务类
 */

public class CacheService {

    private static CacheService mCacheService = null;

    private CacheService() {

    }

    public static CacheService getIntance() {
        if (mCacheService == null) {
            return new CacheService();
        }
        return mCacheService;
    }

    /**
     * 读取用户信息
     *
     * @return
     */
    public UserInfoModel getUserInfo() {
        return (UserInfoModel) CacheUtils.getInstance().loadCache(Constant.USER_INFO);
    }

    /**
     * 缓存用户信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfoModel userInfo) {
        CacheUtils.getInstance().saveCache(Constant.USER_INFO, userInfo);
    }

}
