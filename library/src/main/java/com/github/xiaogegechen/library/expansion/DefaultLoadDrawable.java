package com.github.xiaogegechen.library.expansion;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 默认图片加载失败或者加载中的drawable，
 * 背景+居中显示的文字
 */
public class DefaultLoadDrawable extends Drawable {

    private Paint mBgPaint;
    private Paint mTextPaint;

    /**
     * 显示的内容
     */
    private String mContent;

    public DefaultLoadDrawable(int bgColor, int textColor, float textSize, String content){
        mBgPaint = new Paint ();
        mBgPaint.setColor (bgColor);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle (Paint.Style.FILL);

        mTextPaint = new Paint ();
        mTextPaint.setColor (textColor);
        mTextPaint.setTextAlign (Paint.Align.CENTER);
        mTextPaint.setStyle (Paint.Style.STROKE);
        mTextPaint.setTextSize (textSize);
        mTextPaint.setAntiAlias(true);

        mContent = content;
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds ();
        int centerX = rect.centerX ();
        int centerY = rect.centerY ();
        // 画背景
        canvas.drawRect (rect, mBgPaint);
        // 画文字
        canvas.drawText (mContent, centerX, centerY, mTextPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mBgPaint.setAlpha (alpha);
        mTextPaint.setAlpha (alpha);
        invalidateSelf ();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mBgPaint.setColorFilter (colorFilter);
        mTextPaint.setColorFilter (colorFilter);
        invalidateSelf ();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
