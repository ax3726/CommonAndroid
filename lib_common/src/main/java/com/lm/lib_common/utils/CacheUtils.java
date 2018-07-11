package com.lm.lib_common.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheUtils {

    private static final String CACHE_TAG = CacheUtils.class.getName();

    private static CacheUtils ourInstance = new CacheUtils();

    public static CacheUtils getInstance() {
        return ourInstance;
    }

    private CacheUtils() {
    }


    private CacheMode cacheMode = CacheMode.CACHE_SHORT;
    private String cachePath = Utils.getMemeoryPath("cacheData");


    /**
     * 缓存配置初始化
     *
     * @param cacheMode 缓存模式
     * @param cachePath 缓存文件保存路径
     */
    public void init(CacheMode cacheMode, String cachePath) {
        this.cacheMode = cacheMode;
        this.cachePath = cachePath;
    }

    /**
     * 保存缓存
     * @param url Api路径
     * @param obj 序列化实体对象
     */
    public void saveCache(String url, Object obj) {
        if (null == cachePath) {
            return;
        }
        File path = new File(cachePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        String md5FileName = MD5Utils.encryptMD5(url);
        File file = new File(path, md5FileName);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            Log.d(CACHE_TAG, "file not exist");
        } catch (Exception e) {
            Log.d(CACHE_TAG, "save error");
        }
    }

    /**
     * 读取缓存
     * @param url Api路径
     * @return 缓存的序列化对象
     */
    public Object loadCache(String url) {

        if (null == url && cacheMode != CacheMode.CACHE_NONE) {
            return null;
        }


        Object obj = null;
        String path=cachePath;

        String md5FileName = MD5Utils.encryptMD5(url);
        File file = new File(path, md5FileName);

        if (file.exists() && file.isFile()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                obj = in.readObject();
            } catch (IOException e) {
                Log.d(CACHE_TAG, "file not exist");
            } catch (Exception e) {
                Log.d(CACHE_TAG, "load error");
            }
        }
        return obj;
    }

    /**
     * 删除指定缓存
     * @param url
     * @return
     */
    public boolean removeCache(String url){
        if (null == url && cacheMode != CacheMode.CACHE_NONE) {
            return true;
        }
        String md5FileName = MD5Utils.encryptMD5(url);
        File file = new File(cachePath, md5FileName);
        if (file.exists() && file.isFile()){
            return file.delete();
        }
        return false;
    }

    /**
     * 检查缓存是否超时
     * @param url Api路径
     * @return true:超时 false:未超时
     */
    public boolean isTimeOut(String url){

        if (null == url) {
            return true;
        }

        String md5FileName = MD5Utils.encryptMD5(url);
        File file = new File(cachePath, md5FileName);
        if(file.exists() && file.isFile()) {
            long expiredTime = System.currentTimeMillis() - file.lastModified();
            if (cacheMode == CacheMode.CACHE_SHORT) {
                return (expiredTime > CacheTimeOut.CACHE_SHORT_TIMEOUT);
            } else if (cacheMode == CacheMode.CACHE_MIDDLE) {
                return (expiredTime > CacheTimeOut.CACHE_MIDDLE_TIMEOUT);
            } else if (cacheMode == CacheMode.CACHE_LONG) {
                return (expiredTime > CacheTimeOut.CACHE_LONG_TIMEOUT);
            } else if (cacheMode == CacheMode.CACHE_MAX) {
                return (expiredTime > CacheTimeOut.CACHE_MAX_TIMEOUT);
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * 缓存模式
     */
    public enum CacheMode {

        /**
         * 不缓存
         */
        CACHE_NONE,
        /**
         * 短时间缓存(5分钟)
         */
        CACHE_SHORT,
        /**
         * 中时间缓存(1小时)
         */
        CACHE_MIDDLE,
        /**
         * 长时间缓存(6小时)
         */
        CACHE_LONG,
        /**
         * 超长时间缓存(3天)
         */
        CACHE_MAX

    }

    /**
     * 缓存超时时间
     */
    public class CacheTimeOut {

        /**
         * 不缓存
         */
        public static final long CACHE_NONE_TIMEOUT = 0;

        /**
         * 5分钟
         */
        public static final long CACHE_SHORT_TIMEOUT = 1000 * 60 * 5;

        /**
         * 1小时
         */
        public static final long CACHE_MIDDLE_TIMEOUT = 1000 * 3600;

        /**
         * 6小时
         */
        public static final long CACHE_LONG_TIMEOUT = 1000 * 60 * 60 * 6;

        /**
         * 3天
         */
        public static final long CACHE_MAX_TIMEOUT = 1000 * 60 * 60 * 24 * 3;
    }
}
