package com.github.xiaogegechen.library.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * 图片加载各个阶段拿到图片drawable 的处理器
 */
public interface DrawableGetter {

    /**
     * 返回图片正在加载的drawable
     * @param maxWidth 图片的最大宽度
     * @param context 图片的最大宽度
     * @return 图片正在加载的drawable
     */
    Drawable getLoadingDrawable(int maxWidth, Context context);

    /**
     * 返回图片加载失败的drawable
     * @param maxWidth 图片的最大宽度
     * @param context 图片的最大宽度
     * @return 图片加载失败的drawable
     */
    Drawable getLoadFailedDrawable(int maxWidth, Context context);

    /**
     * 返回加载完成的图片的drawable
     * @param maxWidth 图片的最大宽度
     * @param context context
     * @param bitmap 已经加载好的图片
     * @return 加载完成的图片的drawable
     */
    Drawable getLoadOKDrawable(int maxWidth, Context context, Bitmap bitmap);
}
