package com.github.xiaogegechen.library.expansion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.github.xiaogegechen.library.api.DrawableGetter;

/**
 * DrawableGetter 默认实现类
 */
public class DefaultDrawableGetter implements DrawableGetter {

    private static final String TAG = "DefaultDrawableGetter";

    @Override
    public Drawable getLoadingDrawable(int maxWidth, Context context) {
        Drawable drawable = new DefaultLoadDrawable (Color.parseColor ("#f3f3f3"), Color.parseColor ("#d6d6d6"), 60, "加载中...");
        // 大小适应屏幕
        drawable.setBounds (0, 0, maxWidth, maxWidth / 3);
        return drawable;
    }

    @Override
    public Drawable getLoadFailedDrawable(int maxWidth, Context context) {
        Drawable drawable =new DefaultLoadDrawable (Color.parseColor ("#f3f3f3"), Color.parseColor ("#d6d6d6"), 60, "加载失败");
        // 大小适应屏幕
        drawable.setBounds (0, 0, maxWidth, maxWidth / 3);
        return drawable;
    }

    @Override
    public Drawable getLoadOKDrawable(int maxWidth, Context context, Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable (bitmap);
        // 大小适应屏幕
        int oldWidth = drawable.getIntrinsicWidth ();
        double scale = maxWidth * 1.0 / oldWidth;
        drawable.setBounds (0, 0, maxWidth, (int)(drawable.getIntrinsicHeight () * scale));
        return drawable;
    }
}
