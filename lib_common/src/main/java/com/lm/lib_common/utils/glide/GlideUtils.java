package com.lm.lib_common.utils.glide;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * Created by Administrator on 2018/3/23 0023.
 * Glide工具类
 */

public class GlideUtils {

    private static GlideUtils mGlideUtils;

    private GlideUtils() {

    }

    private Context mContext;

    public static GlideUtils getInstance() {

        return mGlideUtils == null ? new GlideUtils() : mGlideUtils;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }


    public void LoadImage(String imageUrl, ImageView imageView, int... parms) {
        LoadImage(Glide.with(mContext), imageUrl, imageView, parms);

    }

    public void LoadImage(int imageUrl, ImageView imageView, int... parms) {
        LoadImage(Glide.with(mContext), imageUrl, imageView, parms);

    }

    public void LoadImage(File imageUrl, ImageView imageView, int... parms) {
        LoadImage(Glide.with(mContext), imageUrl, imageView, parms);
    }

    public static void loadImage(Context context, String imageUrl, ImageView imageView, int... parms) {
        DrawableTypeRequest<String> load = Glide.with(context).load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

    public static void loadImage(Context context, int imageUrl, ImageView imageView, int... parms) {
        DrawableTypeRequest<Integer> load = Glide.with(context).load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

    public static void loadImage(Context context, File imageUrl, ImageView imageView, int... parms) {
        DrawableTypeRequest<File> load = Glide.with(context).load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

    /**
     * @param requestManager
     * @param imageUrl       图片路径
     * @param imageView      加载图片的控件
     * @param parms          第一个  默认图片  第二个 加载错误图片
     */
    private void LoadImage(RequestManager requestManager, String imageUrl, ImageView imageView, int... parms) {

        DrawableTypeRequest<String> load = requestManager.load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

    /**
     * @param requestManager
     * @param imageUrl       图片路径
     * @param imageView      加载图片的控件
     * @param parms          第一个  默认图片  第二个 加载错误图片
     */
    private void LoadImage(RequestManager requestManager, int imageUrl, ImageView imageView, int... parms) {

        DrawableTypeRequest<Integer> load = requestManager.load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

    /**
     * @param requestManager
     * @param imageUrl       图片路径
     * @param imageView      加载图片的控件
     * @param parms          第一个  默认图片  第二个 加载错误图片
     */
    private void LoadImage(RequestManager requestManager, File imageUrl, ImageView imageView, int... parms) {

        DrawableTypeRequest<File> load = requestManager.load(imageUrl);
        if (parms.length > 0) {
            for (int i = 0; i < parms.length; i++) {
                if (i == 0) {
                    load.placeholder(parms[0]);
                } else if (i == 2) {
                    load.error(parms[1]);
                }
            }
        }
        load.into(imageView);
    }

}
